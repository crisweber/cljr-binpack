(ns cljr-binpack.core)

(defn- pack
  [bin item]
     (cons item bin))

(defn- remaining-space
  [capacity bin item]
     (- capacity (+ (apply + bin) item)))

(defn overflow?
  [capacity bin item]
     (< (remaining-space capacity bin item) 0))

(defn- split-on-fittable-bin
  "Split bins vector on the first bin with enought room (acording to capacity) for the new item to be placed"
  [capacity bins item]
     (split-with (fn [bin] (overflow? capacity bin item)) bins))

(defn first-available-bin
  "Packs the item on the first box where it fits. If there's no bin available, a new bin will be opened."
  [capacity bins item]
     (let [[filled-bins, [bin-to-append & remaining-bins]] (split-on-fittable-bin capacity bins item)]
        (concat filled-bins (cons (pack bin-to-append item) remaining-bins))))

(defn binpack
  [items capacity]
     (let [sorted-items (reverse (sort items))]
        (reduce (partial first-available-bin capacity) [] sorted-items)))
