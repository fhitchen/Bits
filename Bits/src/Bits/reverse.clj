(ns Bits.reverse)

(def n "12370")

(defn get-sequence
  [nn]
  (let [ n (reverse nn)]
    (if (= \0 (first n))
      (subs nn 0 8)
      (apply str (rest n)))))
       

(pr (get-sequence n))
(pr r)
(if (= 0 (first r)) 
  (subs n 0 8)
  (apply  str (rest r)))

(clojure.repl.doc if-let)