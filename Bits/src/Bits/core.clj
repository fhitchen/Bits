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
