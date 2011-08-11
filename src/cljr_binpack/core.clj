(ns cljr-binpack.core)

(defn fit-in-bin?
  "Predicate to evaluate if an item fits in the bin provided"
  [item bin bin-size]
  (<= (+ (apply + bin) item) bin-size))

(defn split-on-fittable-bin
  "Splits the bins sequence in 2 other sequences: The first where item don't fit and the other where item fit"
  [item bins bin-size]
  (split-with (fn [bin] (not (fit-in-bin? item bin bin-size))) bins))

(defn first-available-bin
  "Pack item inside first available bin where item fits. If there's no bin available, a new bin will be opened."
  [bin-size bins item]
  (let [[filled-bins, [bin-to-append & remaining-bins]]  (split-on-fittable-bin item bins bin-size)]
    (filter (comp not empty?)
      (into
        (conj (vec filled-bins) (conj bin-to-append item))
        remaining-bins))))

(defn binpack [items bin-size]
  (let [sorted-items           (reverse (sort items))]
    (reduce (partial first-available-bin bin-size) [] sorted-items)
  )
)
