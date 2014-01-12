(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defmacro domain [name & body]
  `{:tag :domain,
    :attrs {:name (str ~name)},
    :content [~@body]})

(macroexpand '(domain "People", 'man))

(declare handle-things)

(defmacro grouping [name & body]
  `{:tag :grouping,
    :attrs {:name (str ~name)},
    :content [~@(handle-things body)]})

(def accounts (ref #{}))
(defrecord Account [id balance])
(dosync
  alter accounts conj(->Account "CLJ" 1000.00))
(dosync
  alter accounts conj (Account. "FJH" 20000.00))
(class Account)
(Account. "ABC" 123)
(deref accounts)
@accounts

(.getProtectionDomain(.getClass
                       "Hello" ))
(macroexpand (.. "Hello" getClass getProtectionDomain))
(pprint (System/getProperties))
(use 'clojure.pprint)

(.start (new Thread (fn [] (println "Hello" (Thread/currentThread)))))

(def visitors (atom #{}))
(swap! visitors conj "Stu")
(deref visitors)
@visitors

(defn hello [name]
  (swap! visitors conj name)
  (str "Hello, " name)
  )
(hello "Adam Hichens")
@visitors

(str \h \e \y \space \f \a \t \space \h \e \a \d)
(apply str (interleave "Attack at midnight" "some random text to fool everybody"))
(apply str (take-nth 2 "Astotmaec kr aantd ommi dtneixgth tt"))
(if () "Clojure!" "We are in LISP")
(if 0 "Zero is true" "Zero is false")
(defn date [person-1 person-2 & chaperones]
  (println (str person-1 " and " person-2 " went out with " (count chaperones) " chaperones"))) 
(date "Romeo" "Juliet" "Friar Lawrence" "Nurse")

(use 'clojure.string)
(filter #(> (count %) 2) (split "A fine day it is Mickey" #"\W+"))

(defn make-greeter [greeting-prefix]
  (fn [username] (str greeting-prefix ", " username)))
(def hello-greeting (make-greeter "Hello"))
(hello-greeting "World")
(def howdy-greeting (make-greeter "Howdy"))
(howdy-greeting "Pardner")
(def wocher-greeting (make-greeter "Wocher"))
(wocher-greeting "Chutch")
(def foo 10)
(var foo)

(defn square-corners [bottom left size]
  (let [top (+ bottom size)
        right (+ left size)]
    [[bottom left] [top left] [top right] [bottom right]]))

(square-corners 0 0 10)

(defn indexed [coll] (map-indexed vector coll))
(map-indexed vector "asdf")
(indexed "asdf")
(defn index-filter [pred coll]
  (when pred
    (for [[idx elt] (indexed coll) :when (pred elt)] idx)))
(index-filter #{\a \b} "zzzcab")
(index-filter #{\s \a} "asdf")

(index-filter #{1 2 3} [ 10 2 5 19 3])
(use 'clojure.string)
(index-filter #{"one" "two" "three"} (split "Once upon a time two amdocs programmers once wrote three programs." #"\s"))
(nth (index-filter #{:h} [:t :t :h :t :h :t :t :t :h :h]) 2)
(defn ^String shout [^String s] (.toUpperCase s))
(shout "Shit")
(meta #'shout)
(meta #'str)


(defn get-sequence
  "Take 9 char BAN id, discard the check digit and extract the actual # for the range check"
  [nn]
  (if (< (count nn) 9)
    (errlog-timestamped (str "BAN id '" nn "' is less than 9 chars, can not process."))
    (let [s (subs nn 0 8)
          n (reverse s)]
      (if (= \0 (first n))
        s
        (apply str n)))))

(get-sequence "100000000")

(defn whole-numbers [] (iterate inc 1))
(take 10 ( whole-numbers))
(take 10 (filter even? (whole-numbers)))
(take 10 (filter odd? (whole-numbers)))
(interleave (whole-numbers) ["a" "b" "c" "d"])

(take-while (complement #{\a\e\i\o\u}) "the-quick-brown-fox")
(drop-while (complement #{\a\e\i\o\u}) "the-quick-brown-fox")

(def x (for [i (range 10)] (do (println i) i)))
(doall x)
(dorun x)
(first (.getBytes "hello"))
(rest (.getBytes "hello"))
(cons (int \h) (.getBytes "ello"))
(first (System/getProperties))
(rest (System/getProperties))
(reverse "hello")
(apply str (reverse "hello"))
(re-seq #"\w+" "The quick brown fox")
(sort (re-seq #"\w+" "The quick brown fox"))
(drop 2 (re-seq #"\w+" "The quick brown fox"))
(map #(.toUpperCase %) (re-seq #"\w+" "The quick brown fox"))

(import '(java.io File))
(.listFiles (File. "."))

(seq (.listFiles (File. ".")))

(map #(.getName %) (seq (.listFiles (File. "."))))
(map #(.getName %) (.listFiles (File. ".")))

(count (file-seq (File. ".")))

(map #(.lastModified %) (.listFiles (File. ".")))

(defn minutes-to-millis [mins] (* mins 1000 60))
(minutes-to-millis 1)
(defn recently-modified? [file]
  (> (.lastModified file)
     (- (System/currentTimeMillis) (minutes-to-millis 30))))

(map #(.getCanonicalPath %) (filter recently-modified? (file-seq (File. "."))))

(use '[clojure.java.io :only (reader)])
(with-open [r (reader "/Users/fhitchen/git/Bits/Bits/src/Bits/core.clj")]
  (count (line-seq r)))

(defn non-blank? [line] (if (re-find #"\S" line) true false))
(defn non-svn? [file] (not (.contains (.toString file) ".svn")))
(defn clojure-source? [file] (.endsWith (.toString file) ".clj"))

(defn clojure-loc [base-file]
  (reduce
    +
    (for [file (file-seq base-file)
          :when (and (clojure-source? file) (non-svn? file))]
      (with-open [rdr (reader file)]
        (println (.getName file))
        (count (filter non-blank? (line-seq rdr)))))))
  

(clojure-loc (java.io.File. "/Users/fhitchen/Downloads/code"))

(use '[clojure.xml :only (parse)])

(xml-seq (parse (java.io.File. "/Users/fhitchen/Downloads/code/data/sequences/compositions.xml")))

(for [x (xml-seq (parse (java.io.File. "/Users/fhitchen/Downloads/code/data/sequences/compositions.xml")))
      :when (= :composition (:tag x))]
  (:composer (:attrs x)))

(peek '(1 2 3))
(pop '(1 2 3))
(rest '())
(pop '())
(peek [1 2 3])
(pop [ 1 2 3])
(get [:a :b :c] 1)
(get [:a :b :c] 5)
([:a :b :c] 1)
([:a :b :c] 5)
(assoc [0 1 2 3 4] 2 :two) 
(subvec [1 2 3 4 5] 3)
(subvec [1 2 3 4 5] 1 3)
(take 2 (drop 1 [1 2 3 4 5]))
(get {:sundance "spaniel" :darwin "beagle"} :darwin)
(get {:sundance "spaniel" :darwin "beagle"} :snoopy)
({:sundance "spaniel" :darwin "beagle"} :darwin)
(:darwin {:sundance "spaniel" :darwin "beagle"})
(:snoopy {:sundance "spaniel" :darwin "beagle"})
(def score {:stu nil :joey 100})
(:stu score)
(contains? score :stu)
(get score :stu :score-not-found)
(get score :aaron :score-not-found)

(def song {:name "Agnus Dei"
           :artist "Krzysztof Penderecki"
           :album "Polish Requiem"
           :genre "Classical"})

(assoc song :kind "MPEG Audio File")
(dissoc song :genre)
(select-keys song [:name :artist])
(merge song {:size 8118166 :time 507245})

(merge-with 
   concat 
   {:flintstone, ["Fred"], :rubble ["Barney"]}
   {:flintstone, ["Wilma"], :rubble ["Betty"]}
   {:flintstone, ["Pebbles"], :rubble ["Bam-Bam"]})

(concat {:f ["f"] :b ["b"]} {:f ["a"] :b ["c"]})

;sets
(use 'clojure.set)
(def languages #{"java" "c" "d" "clojure"})
(def beverages #{"java" "chai" "pop"})
(union languages beverages)
(difference languages beverages)
(intersection languages beverages)
(select #(= 1 (.length %)) languages)


(def compositions 
  #{{:name "The Art of the Fugue" :composer "J. S. Bach"}
    {:name "Musical Offering" :composer "J. S. Bach"}
    {:name "Requiem" :composer "Giuseppe Verdi"}
    {:name "Requiem" :composer "W. A. Mozart"}})
(def composers
  #{{:composer "J. S. Bach" :country "Germany"}
    {:composer "W. A. Mozart" :country "Austria"}
    {:composer "Giuseppe Verdi" :country "Italy"}})
(def nations
  #{{:nation "Germany" :language "German"}
    {:nation "Austria" :language "German"}
    {:nation "Italy" :language "Italian"}})

(rename compositions {:name :title})
(select #(= (:name %) "Requiem") compositions)
(project compositions [:name])
(for [m compositions c composers] (concat m c))
(join compositions composers)
(join composers nations {:country :nation})
(project
  (join
    (select #(= (:name %) "Requiem") compositions)
    composers)
  [:country])

(defrecord Message [user text])
(def messages (ref ()))
(def backup-agent (agent "messages-backup.clj"))

(defn add-messages-with-backup [msg]
  (dosync
    (let [snapshot (commute messages conj msg)]
      (send-off backup-agent (fn [filename]
                               (spit filename snapshot)
                               filename))
                snapshot)))

(add-messages-with-backup (Message. "John" "Message One"))
(add-messages-with-backup (Message. "Jill" "Message Two"))

(def ^:dynamic foo 10)
(meta foo)
foo
(.start (Thread. (fn [] (println foo))))

(binding [foo 42] foo)
(defn print-foo [] (println foo))
(print-foo)
(let [foo "let foo"] (print-foo))
(binding [foo "bound foo"] (print-foo))