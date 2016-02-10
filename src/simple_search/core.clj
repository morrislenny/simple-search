(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_11_200_1000
        simple-search.knapsack-examples.knapPI_11_1000_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_13_200_1000
        simple-search.knapsack-examples.knapPI_13_1000_1000
        simple-search.knapsack-examples.knapPI_16_20_1000
        simple-search.knapsack-examples.knapPI_16_200_1000
        simple-search.knapsack-examples.knapPI_16_1000_1000
        ))

;;; An answer will be a map with (at least) four entries:
;;;   * :instance
;;;   * :choices - a vector of 0's and 1's indicating whether
;;;        the corresponding item should be included
;;;   * :total-weight - the weight of the chosen items
;;;   * :total-value - the value of the chosen items

(defn included-items
  "Takes a sequences of items and a sequence of choices and
  returns the subsequence of items corresponding to the 1's
  in the choices sequence."
  [items choices]
  (map first
       (filter #(= 1 (second %))
               (map vector items choices))))

(defn random-answer
  "Construct a random answer for the given instance of the
  knapsack problem."
  [instance]
  (let [choices (repeatedly (count (:items instance))
                            #(rand-int 2))
        included (included-items (:items instance) choices)]
    {:instance instance
     :choices (vec choices)
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))}))

;;; It might be cool to write a function that
;;; generates weighted proportions of 0's and 1's.


(defn score
  "Returns total-value of given answer unless that answers total-weight is over capacity
  then it returns capacity minus weight."
  [answer]
  (if (> (:total-weight answer)
         (:capacity (:instance answer)))
    (- (:capacity (:instance answer)) (:total-weight answer))
    (:total-value answer)))

(defn add-score
  "Computes the score of an answer and inserts a new :score field
   to the given answer, returning the augmented answer."
  [answer]
  (assoc answer :score (score answer)))

(defn random-search
  [instance max-tries]
  (apply max-key :score
         (map add-score
              (repeatedly max-tries #(random-answer instance)))))



(defn format-answer
  "Takes original set of choices, and inserts the new modified choices."
  [answer]
  (let [included (included-items (:items (:instance answer)) (:choices answer))]
    {:instance (:instance answer)
     :choices (:choices answer)
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))}))


(defn rand-choose-index
  "Takes a vector (to-flip) of indexs to modify in choices, and a range of index size in choices.
  Returns a new unique vector of indexs with one more unique random index."
  [to-flip
   choice-size]
  (loop [random-choice (rand-int choice-size)]
    (if (= (some #(= random-choice %) to-flip) true)
      (recur (rand-int choice-size))
      (vec (cons random-choice to-flip)))))


(defn rand-choose-indices
  "Takes a mutation rate (num-flips) and the size of choices.
  Returns a vector, num-flips long, of unique indexs to mutate."
  [num-flips choice-size]
  (loop [indices []]
    (if (= num-flips (count indices))
      indices
      (recur (rand-choose-index indices choice-size)))))


(defn modifyChoices
  "Takes a vector of choices and a mutation rate (num-flips).
  Returns a modified vector of choices with the specified mutations rate."
  [choices
   num-flips]
  (let [to-flip (sort (rand-choose-indices num-flips (count choices)))]
    ;;(println "==================NEW RUN=====================")
    ;;(println choices)
    ;;(println to-flip)
    (loop [flipped []
           x 0]
      (if-not (= x (count choices))
          (if (= (some #{x} to-flip) x) ;;flip at index 1 or 0 to 0 or 1 then add to flipped
            (do
            ;;  (print "will flip ")(println x)
              (if (= (get choices x) 1)
                (do
              ;;    (println "changes to 0")
                  (recur (vec (conj flipped 0)) (inc x)))
                (do
                ;;  (println "changes to 1")
                  (recur (vec (conj flipped 1)) (inc x)))))
            (do
             ;; (print "won't flip ")(println x)
              (recur (vec (conj flipped (get choices x))) (inc x))))
        flipped))))




(defn insert-updated-choices
  "Takes an answer and a mutation rate (num-flips). Returns evolved choices."
  [answer num-flips]
    (let [choices (modifyChoices (:choices answer) num-flips)]
      (assoc-in answer [:choices] choices)))




;;; instance- the type of knapsack problem (data set)
;;; num-runs- determines the number of generations
;;; num-flips- determines the permutation rate
(defn evolve-answer
  "Takes an instance, number of mutations (num-runs), and a rate of mutations (num-flips).
  Returns the best answer of mutations."
  [instance num-runs num-flips]
    (loop [parent-answer (random-search instance 1)
           child-answer (add-score (format-answer (insert-updated-choices parent-answer num-flips)))
           x 0]
      ;;(println x)
      ;;(print "parent score ") (println (:score parent-answer))
      ;;(print "child score ") (println (:score child-answer))
      (if-not (= x num-runs)
          (if (< (:score parent-answer) (:score child-answer))
            (recur child-answer (add-score (format-answer (insert-updated-choices child-answer num-flips))) (inc x))
            (recur parent-answer (add-score (format-answer (insert-updated-choices parent-answer num-flips))) (inc x)))
        parent-answer)))



(defn best-evolved-search
  "Takes instance, attempts, number of mutations, and rate of mutations.
  Returns the best answer of the attempts."
  [instance max-tries num-mutations num-flips]
  (apply max-key :score
         (map add-score
              (repeatedly max-tries #(add-score (evolve-answer instance num-mutations num-flips))))))

;;; knapPI_16
;;;
;;; Ours
(:score (best-evolved-search knapPI_16_20_1000_1 10 1000 1))
(:score (best-evolved-search knapPI_16_20_1000_1 10 1000 4))
(:score (best-evolved-search knapPI_16_200_1000_13 10 1000 20))
(:score (best-evolved-search knapPI_16_200_1000_13 10 1000 40))
(:score (best-evolved-search knapPI_16_1000_1000_5 10 1000 100))
(:score (best-evolved-search knapPI_16_1000_1000_5 10 1000 200))

(:score (best-evolved-search knapPI_16_20_1000_2 10 1000 1))
(:score (best-evolved-search knapPI_16_20_1000_2 10 1000 4))
(:score (best-evolved-search knapPI_16_200_1000_14 10 1000 20))
(:score (best-evolved-search knapPI_16_200_1000_14 10 1000 40))
(:score (best-evolved-search knapPI_16_1000_1000_6 10 1000 100))
(:score (best-evolved-search knapPI_16_1000_1000_6 10 1000 200))

;;; Random
(:score (random-search knapPI_16_20_1000_1 1000))
(:score (random-search knapPI_16_200_1000_13 1000))
(:score (random-search knapPI_16_200_1000_5 1000))

(:score (random-search knapPI_16_20_1000_2 1000))
(:score (random-search knapPI_16_200_1000_14 1000))
(:score (random-search knapPI_16_200_1000_6 1000))

;;; knapPI_13
;;;
;;; Ours
(:score (best-evolved-search knapPI_13_20_1000_1 10 1000 1))
(:score (best-evolved-search knapPI_13_20_1000_1 10 1000 4))
(:score (best-evolved-search knapPI_13_200_1000_13 10 1000 20))
(:score (best-evolved-search knapPI_13_200_1000_13 10 1000 40))
(:score (best-evolved-search knapPI_13_1000_1000_5 2 1000 100))
(:score (best-evolved-search knapPI_13_1000_1000_5 10 1000 200))

(:score (best-evolved-search knapPI_13_20_1000_2 10 1000 1))
(:score (best-evolved-search knapPI_13_20_1000_2 10 1000 4))
(:score (best-evolved-search knapPI_13_200_1000_14 10 1000 20))
(:score (best-evolved-search knapPI_13_200_1000_14 10 1000 40))
(:score (best-evolved-search knapPI_13_1000_1000_6 2 1000 100))
(:score (best-evolved-search knapPI_13_1000_1000_6 2 1000 200))

;;; Random
(:score (random-search knapPI_13_20_1000_1 1000))
(:score (random-search knapPI_13_200_1000_13 1000))
(:score (random-search knapPI_13_200_1000_5 1000))

(:score (random-search knapPI_13_20_1000_2 1000))
(:score (random-search knapPI_13_200_1000_14 1000))
(:score (random-search knapPI_13_200_1000_6 1000))




;;;Lenny's tests


;;;Best Evolved: knapPI_1000_1000.clf
(:score (best-evolved-search knapPI_11_1000_1000_1 10 1000 100))
(:score (best-evolved-search knapPI_11_1000_1000_1 10 1000 200))
(:score (best-evolved-search knapPI_11_1000_1000_15 10 1000 100))
(:score (best-evolved-search knapPI_11_1000_1000_15 10 1000 200))
(:score (best-evolved-search knapPI_11_1000_1000_30 10 1000 100))
(:score (best-evolved-search knapPI_11_1000_1000_30 10 1000 200))

;;;Random: knapPI_1000_1000.clf
(:score (random-search knapPI_11_1000_1000_1 1000))
(:score (random-search knapPI_11_1000_1000_15 1000))
(:score (random-search knapPI_11_1000_1000_30 1000))

;;Best Evolved: knapPI_11_200_1000.clf
(:score (best-evolved-search knapPI_11_200_1000_1 10 1000 20))
(:score (best-evolved-search knapPI_11_200_1000_1 10 1000 40))
(:score (best-evolved-search knapPI_11_200_1000_15 10 1000 20))
(:score (best-evolved-search knapPI_11_200_1000_15 10 1000 40))
(:score (best-evolved-search knapPI_11_200_1000_30 10 1000 20))
(:score (best-evolved-search knapPI_11_200_1000_30 10 1000 40))

;;;Random: knapPI_11_200_1000.clf
(:score (random-search knapPI_11_200_1000_1 1000))
(:score (random-search knapPI_11_200_1000_15 1000))
(:score (random-search knapPI_11_200_1000_30 1000))

;;Best Evolved: knapPI_11_20_1000
(:score (best-evolved-search knapPI_11_20_1000_1 10 1000 2))
(:score (best-evolved-search knapPI_11_20_1000_1 10 1000 4))
(:score (best-evolved-search knapPI_11_20_1000_15 10 1000 2))
(:score (best-evolved-search knapPI_11_20_1000_15 10 1000 4))
(:score (best-evolved-search knapPI_11_20_1000_30 10 1000 2))
(:score (best-evolved-search knapPI_11_20_1000_30 10 1000 4))

;;Random: knapPI_11_20_1000
(:score (random-search knapPI_11_20_1000_1 1000))
(:score (random-search knapPI_11_20_1000_15 1000))
(:score (random-search knapPI_11_20_1000_30 1000))





;;; Just random test things
;;;
;;(add-score (evolve-answer knapPI_16_20_1000_1 1000 4))
;;(random-search knapPI_16_20_1000_1 10000)
;;(add-score (evolve-answer knapPI_16_20_1000_1 1000 10))
;;(add-score (format-answer (insert-updated-choices (random-search knapPI_16_20_1000_1 1))))

