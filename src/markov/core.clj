(ns markov.core
  (:gen-class))

(defn random-weighted-choice
  [choices]
  (let [total (reduce + (map second choices))
        target (rand-int total)]
    (loop [[pair & remaining] (seq choices)
           accumulated (second pair)]
      (if (> accumulated target)
        (first pair)
        (recur remaining
               (+ accumulated
                  (second (first remaining))))))))

(defn next-markov-choice
  [word followers]
  (let [choices (get followers word)]
    (if (nil? choices)
      nil
      (random-weighted-choice choices))))

(defn generate-markov-seq
  ([followers] (let [word (first (rand-nth (seq followers)))]
                 (generate-markov-seq word followers)))
  ([word followers]
   (cons word
         (lazy-seq (when-let [next-word (next-markov-choice word followers)]
                     (generate-markov-seq next-word followers))))))

(defn -main
  [& args]
  (let [words-seq (mapcat #(re-seq #"\b\w+\b" %)
                          args)
        pairs (map vector
                words-seq
                (drop 1 words-seq))
        followers (reduce (fn [result [w1 w2 :as pair]]
            (update-in result
                       pair
                       (fn [x]
                         (if (nil? x)
                           1
                           (inc x)))))
        {}
        pairs)]
    (println (clojure.string/join " "
                                  (generate-markov-seq followers)))))


