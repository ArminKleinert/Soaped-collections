package kleinert.soap

object MapUtils {
    inline fun <reified V> getVOrElse(map: Map<Any?, Any?>, k: Any?, elseFn: (Any?) -> V): V {
        val v = map[k]
        if (v !is V) return elseFn(v)
        return v
    }

    inline fun <reified V> getVOrDefault(map: Map<Any?, Any?>, k: Any?, default: V): V {
        val v = map[k]
        if (v !is V) return default
        return v
    }

    inline fun <reified V> getV(map: Map<Any?, Any?>, k: Any?): V? {
        val v = map[k]
        if (v !is V) return null
        return v
    }

    inline fun <reified V> getInOrElse(map: Map<Any?, Any?>, keys: Iterable<Any?>, elseFn: (Any?) -> V): V {
        var inner: Any? = map
        for (key in keys) {
            if (inner !is Map<*, *>) return elseFn(inner)
            inner = inner[key]
        }
        if (inner !is V) return elseFn(inner)
        return inner
    }

    inline fun <reified V> getInOrDefault(map: Map<Any?, Any?>, keys: Iterable<Any?>, default: V): V {
        var inner: Any? = map
        for (key in keys) {
            if (inner !is Map<*, *>) return default
            inner = inner[key]
        }
        if (inner !is V) return default
        return inner
    }

    inline fun <reified V> getIn(map: Map<Any?, Any?>, keys: Iterable<Any?>): V? {
        var inner: Any? = map
        for (key in keys) {
            if (inner !is Map<*, *>)
                return null
            inner = inner[key]
        }
        if (inner !is V) return null
        return inner
    }

    inline fun getAInOrElse(map: Map<Any?, Any?>, keys: Iterable<Any?>, elseFn: (Any?) -> Any?): Any? {
        var inner: Any? = map
        for (key in keys) {
            inner =
                if (inner is List<*> && key is Number) inner[key.toInt()]
                else if (inner is Map<*, *>) inner[key]
                else return elseFn(inner)
        }
        return inner
    }

    fun getAInOrDefault(map: Map<Any?, Any?>, keys: Iterable<Any?>, default: Any?): Any? {
        var inner: Any? = map
        for (key in keys) {
            inner =
                if (inner is List<*> && key is Number) inner[key.toInt()]
                else if (inner is Map<*, *>) inner[key]
                else return default
        }
        return inner
    }

    fun getAIn(map: Map<Any?, Any?>, keys: Iterable<Any?>): Any? {
        var inner: Any? = map
        for (key in keys) {
            inner =
                if (inner is List<*> && key is Number) inner[key.toInt()]
                else if (inner is Map<*, *>) inner[key]
                else return null
        }
        return inner
    }
}