(ns cljr-binpack.test.core
  (:use [cljr-binpack.core])
  (:use [clojure.test]))

(def items [4 8 5 1 7 6 1 4 2 2])

(deftest item-fits-on-empty-bin
  (is (not (overflow? 1 [] 1)))
  )

(deftest item-dont-fit-on-full-bin
  (is (overflow? 1 [1] 1))
  )

(deftest item-fits-when-there-is-enough-space
  (is (not (overflow? 2 [1] 1)))
  )

(deftest items-will-be-packet-in-four-full-bins
  (is (= [10 10 10 10] (map #(apply + %) (binpack items 10)))))
