(ns reagent2.dom
  (:require ["react-dom" :as react-dom]
            ["react" :as react]))

(defn div [props & children]
  (apply react/createElement "div" props children))

(defn button [props & children]
  (apply react/createElement "button" props children))
