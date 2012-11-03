(ns zolodeck.utils.calendar
  (:use [clj-time.format :only (parse unparse formatters formatter)]
        [clj-time.core :only (date-time year month day)]
        [clj-time.coerce :only (to-date-time)])
  (:import com.joestelmach.natty.Parser
           java.util.TimeZone
           java.util.Locale
           java.util.Date
           java.text.SimpleDateFormat))

(Locale/setDefault Locale/US)
(TimeZone/setDefault (TimeZone/getTimeZone "GMT"))

(declare simple-date-format)

(def yyyy-MM-dd-HH-mm "yyyy-MM-dd HH:mm")

(def yyyy-MM-dd-format (simple-date-format "yyyy-MM-dd"))

(defn date-string->instant [format date-string]
  (when date-string
    (.toDate (parse (or (formatters format) (formatter format)) date-string))))

(defn millis->instant [millis]
  (java.sql.Timestamp. millis))

(def BEGINNING-OF-TIME (date-time 1971 1 1))

(def BEGINNING-OF-TIME-MILLIS (.getMillis BEGINNING-OF-TIME))

(defn now []
  (System/currentTimeMillis))

(defn now-instant []
  (millis->instant (now)))

(defn to-seconds [yyyy-MM-dd-string]
  (/ (.getTime (date-string->instant "yyyy-MM-dd" yyyy-MM-dd-string)) 1000))

(defn fuzzy-parse [date-string]
  (let [groups (.parse (Parser.) date-string)]
    (if-not (empty? groups)
      (first (.getDates (first groups))))))

(defn this-year []
  (-> (now) to-date-time year))

(defn parse-birthday [date-string]
  (if date-string
    (let [d (fuzzy-parse date-string)
          dt (to-date-time d)]
      (if dt
        (if (= (year dt) (this-year))
          (.toDate (date-time 1900 (month dt) (day dt)))
          d)))))

(defn simple-date-format
  ([format-string]
     (simple-date-format format-string "UTC"))
  ([format-string tz-string]
     (doto ^SimpleDateFormat (SimpleDateFormat. format-string)
           (.setTimeZone (TimeZone/getTimeZone tz-string)))))

(defn utc-datetime-format
 "Return a 'yyyy-MM-dd HH:mm' date format enforcing UTC semantics. Not thread safe!"
 ^SimpleDateFormat []
 (simple-date-format yyyy-MM-dd-HH-mm))

(defn date-to-string
  "Converts date to yyyy-MM-dd format"
  ([^Date d]
     (date-to-string d (utc-datetime-format)))
  ([^Date d ^SimpleDateFormat formatter]
     (if d (.format formatter d))))

(defn date-to-simple-string [d]
  (if d
    (date-to-string d yyyy-MM-dd-format)))