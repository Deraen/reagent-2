(ns reagent2.core)

(defmacro defnc [n bindings-vec & body]
  `(defn ~n ~bindings-vec
     (reactive-wrapper
       (fn ~bindings-vec
         ~@body))))
