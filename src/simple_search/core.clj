(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_16_20_1000))

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
  "Takes the total-weight of the given answer unless it's over capacity,
   in which case we return 0."
  [answer]
  (if (> (:total-weight answer)
         (:capacity (:instance answer)))
    0
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

(:choices (random-search knapPI_16_20_1000_1 100
))





(defn rand-choose-index
  [to-flip
   choice-size]
  (loop [random-choice (rand-int choice-size)]
    (if (= (some #(= random-choice %) to-flip) true)
      (recur (rand-int choice-size))
      (vec (cons random-choice to-flip)))))



(rand-choose-index [2 5 3 7] 10)

(defn rand-choose-indices
  [num-flips choice-size]
  (loop [indices []]
    (if (= num-flips (count indices))
      indices
      (recur (rand-choose-index indices choice-size)))))

(sort (rand-choose-indices 5 10))

(defn modifyChoices
  [choices
   num-flips]
  (let [to-flip (sort (rand-choose-indices num-flips (count choices)))
        flipped []]
    (loop [flipped []
           x 0]
      (if-not (= x (- (count choices) 1))
          (if (= (some #{x} to-flip) x) ;;flip at index 1 or 0 to 0 or 1 then add to flipped
            ) ;;stuff needed here
        flipped))))


(modifyChoices (:choices (random-search knapPI_16_20_1000_1 100)) 5)

(= (some #{5} [1 2 3 4]) 5)
