(ns reagent2.core)

(defmacro defnc [n bindings-vec & body]
  `(defn ~n [props#]
     (reactive-wrapper
       (fn ~bindings-vec
         ~@body)
       props#)))
