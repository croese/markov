(defproject markov "0.1"
  :description "Simple command-line markov generator"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ^:skip-aot markov.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
