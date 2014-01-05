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