(ns reagent2.demo
  (:require [reagent2.core :as r :refer [$ defnc]]
            [reagent2.dom :as d]
            ["react-dom" :as react-dom]

            [reagent.ratom :as ra]))

(defonce state (ra/atom 1))

;; Defnc passes children from the props to the function
(defnc test-c [props a b]
  (d/div #js {}
         a
         b))

(defnc main []
  (d/div
    #js {:className "foo"}
    ($ test-c nil "hello-world" @state)
    (d/button
      #js {:onClick (fn [_e]
                      (js/console.log "click handler")
                      (swap! state inc))}
      "+")))

(defn ^:dev/after-load init []
  (react-dom/render ($ main) (.getElementById js/document "app")))

(init)
