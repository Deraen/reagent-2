(ns reagent2.core
  (:require-macros reagent2.core)
  (:require ["react" :as react]
            [reagent.ratom :as ratom]
            [goog.object :as gobj]))

(defn $ [& args]
  (apply react/createElement args))

(defn reactive-render [^cljs state]
  (apply (.-f state) (.-props state) (.-children (.-props state))))

(defn reactive-wrapper [f props]
  (let [[_ update-count] (react/useState 0)
        state-ref (react/useRef)

        _ (when-not (.-current state-ref)
            (let [obj #js {}]
              (set! (.-render obj) (fn [] (update-count inc)))
              (set! (.-current state-ref) obj)))

        reagent-state (.-current state-ref)

        ;; FIXME: Access cljsRatom using interop forms
        rat ^ratom/Reaction (gobj/get reagent-state "cljsRatom")]
    (react/useEffect
      (fn mount []
        (fn unmount []
          (some-> (gobj/get reagent-state "cljsRatom") ratom/dispose!)))
      ;; Ignore props - only run effect once on mount and unmount
      #js [])

    (set! (.-f reagent-state) f)
    (set! (.-props reagent-state) props)

    (if (nil? rat)
      (ratom/run-in-reaction
        #(reactive-render reagent-state)
        reagent-state
        "cljsRatom"
        #(.render %)
        {:no-cache true})
      (._run rat false))))
