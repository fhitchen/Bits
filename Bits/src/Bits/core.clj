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
  alter accounts conj(Account. "FJH" 20000.00))
(class Account)
(Account. "ABC" 123)
(deref accounts)

(.getProtectionDomain(.getClass
                       "Hello" ))


