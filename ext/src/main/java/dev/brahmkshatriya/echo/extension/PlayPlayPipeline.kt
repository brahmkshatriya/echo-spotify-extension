package dev.brahmkshatriya.echo.extension

/** Pure Kotlin port of PlayPlay response mixing and key transform (no native/AAR runtime). */
internal class PlayPlayPipeline {
    private val leaves = PlayPlayLeaves()
    private val g = HashMap<String,Long>()
    private fun r(e:Map<String,Long>,name:String):Long = e[name] ?: g[name] ?: 0L
    private fun s(e:MutableMap<String,Long>,name:String,value:Long) {
        if (e.containsKey(name)) e[name] = value else g[name] = value
    }
    private fun g(name:String):Long = g[name] ?: 0L
    private val wrappers = longArrayOf(
        0xcc3618L, 0xcc3654L, 0xcc36c8L, 0xcc3758L, 0xcc3798L, 0xcc3804L, 0xcc3874L, 0xcc38b8L,
        0xcc3930L, 0xcc3988L, 0xcc39dcL, 0xcc3a28L, 0xcc3a90L, 0xcc3b2cL, 0xcc3ba8L, 0xcc3c10L,
        0xcc3cb0L, 0xcc3d14L, 0xcc3d64L, 0xcc3df4L, 0xcc3e44L, 0xcc3eb0L, 0xcc3f34L, 0xcc3f80L,
        0xcc4024L, 0xcc4088L, 0xcc40d4L, 0xcc4154L, 0xcc4198L, 0xcc4230L, 0xcc42bcL, 0xcc42f8L,
        0xcc4340L, 0xcc4380L, 0xcc43ccL, 0xcc4430L, 0xcc44a8L, 0xcc4508L, 0xcc4580L, 0xcc45e8L,
        0xcc4664L, 0xcc46bcL, 0xcc472cL, 0xcc4790L, 0xcc4810L, 0xcc4870L, 0xcc48d4L, 0xcc4958L,
        0xcc498cL, 0xcc49ecL, 0xcc4a40L, 0xcc4a8cL, 0xcc4accL, 0xcc4b24L, 0xcc4b98L, 0xcc4be0L,
        0xcc4c30L, 0xcc4c80L, 0xcc4ce0L, 0xcc4d28L, 0xcc4d74L, 0xcc4de4L, 0xcc4e78L, 0xcc4edcL,
    )
    private fun leaf(slot:Int,x:Long,y:Long) {
        val result=leaves.evaluate(slot,x,y)
        g["WR_X0"]=result.first
        g["WR_X1"]=result.second
    }
    private fun swap(x:Long) {g["SWAPPED"] = (x shl 32) or ((x shr 32) and 0xffffffffL)}
    private fun fold() {
        val a=g("WR_X0");val b=g("WR_X1")
        g["WR_X0"] = (((a and 0xffffffffL) xor ((a shr 32) and 0xffffffffL)) +
            ((b and 0xffffffffL) xor ((b shr 32) and 0xffffffffL))) and 0xffffffffL
    }
    private fun chain(vararg slots:Long) {for (slot in slots)leaf(slot.toInt(),g("WR_X0"),g("WR_X1"))}
    private fun swappedLeaf(slot:Int) {swap(g("WR_X0"));leaf(slot,g("SWAPPED"),g("WR_X1"))}
    private fun optionalLeaf(bit:Int, slot:Int) {if (((g("WR_X0") shr bit) and 1L)!=0L)swappedLeaf(slot)}
    private fun repeatLeaf(slot:Int, shift:Int, swapFirst:Boolean, limit:Int=8) {
        if (swapFirst) {swap(g("WR_X0"));g["WR_X0"]=g("SWAPPED")}
        for (count in 0 until limit) {
            leaf(slot,g("WR_X0"),g("WR_X1"))
            if (count >= (((g("WR_X0") and 0xffffffffL) shr shift) and 7L).toInt())return
        }
        error("PlayPlay repeated wrapper did not converge at slot $slot")
    }
    private fun optionalPrefix(bit:Int,slot:Int) {
        val trigger=(g("WR_X0") shr bit) and 1L
        swap(g("WR_X0"));g["WR_X0"]=g("SWAPPED")
        if (trigger!=0L)leaf(slot,g("WR_X0"),g("WR_X1"))
    }
    private fun wrapperPair(shiftA:Long,slotA:Long,shiftB:Long,slotB:Long):Pair<Long,Long> {
        val first=(g("WR_X0") shr shiftA.toInt()) and 1L
        if (first!=0L)swappedLeaf(slotA.toInt())
        val x=if(first!=0L) (g("WR_X0") shr 32) and 0xffffffffL else g("WR_X0")
        val second=(x shr shiftB.toInt()) and 1L
        if(second!=0L){
            if(first!=0L)swappedLeaf(slotB.toInt())
            else leaf(slotB.toInt(),g("WR_X0"),g("WR_X1"))
        }
        return first to second
    }
    private fun selected(selector:Long,x:Long,y:Long) {
        call("wrapper_run",wrappers[(selector and 63L).toInt()],x,y)
    }
    private fun ror32(word:Long,shift:Long) {
        g["ROR_RESULT"] = java.lang.Integer.rotateRight(word.toInt(),shift.toInt() and 31).toLong() and 0xffffffffL
    }
    private fun table32(offset:Long,index:Long) {
        val i=(offset / 4L + (index and 255L)).toInt()
        require(i in 0 until 768) {"Response table index outside verified range: $i"}
        g["TABLE_RESULT"] = leaves.lookup(i)
    }
    private fun exceptionStep(name:String, swapped:Long) {
        val x=if (swapped!=0L) (g("EX1") or (g("EX0") shl 32)) else (g("EX0") or (g("EX1") shl 32))
        call(name,x,g("EX2") or (g("EX3") shl 32))
    }
    private fun responseMixingEntry() {
        responsePrefix()
        call("response_cc1558_complete",g("RESPONSE_CALL_X1"),g("RESPONSE_CALL_X2"))
        leaf(271,g("CC_RESULT_X0"),g("CC_RESULT_X1"))
        g["MIX_NEXT_SLOT"]=54L;g["MIX_NEXT_X1"]=g("WR_X0");g["MIX_NEXT_X2"]=g("WR_X1")
    }
    private fun responsePrefix() {
        val field = responseInput
        require(field.size == 16)
        fun w(pos:Int):Long = (field[pos].toLong() and 255L) or
            ((field[pos+1].toLong() and 255L) shl 8) or
            ((field[pos+2].toLong() and 255L) shl 16) or
            ((field[pos+3].toLong() and 255L) shl 24)
        fn_response_prefix_math(longArrayOf(w(0),w(4),w(8),w(12)))
    }
    private lateinit var responseInput:ByteArray
    private fun responseMixingExceptions() {
        call("response_mixing_preexception")
        call("throw_cbc930",g("MIX_NEXT_X1"),g("MIX_NEXT_X2"))
        if ((g("EX0") and (1L shl 12)) !=0L)exceptionStep("throw_cbe7a8",0L)
        exceptionStep("throw_cbe868",1L)
        exceptionStep("throw_cc05f0",0L)
        exceptionStep("throw_cc01a8",0L)
        val first=g("EX0") or (g("EX1") shl 32)
        val second=g("EX2") or (g("EX3") shl 32)
        g["EX_MIX_FIRST"]=first;g["EX_MIX_SECOND"]=second
        g["MIX_NEXT_X1"]=first;g["MIX_NEXT_X2"]=second
        if ((g("EX0") and (1L shl 11)) != 0L) g["MIX_NEXT_SLOT"]=116L
        else if ((g("EX1") and (1L shl 16)) != 0L) {
            g["MIX_NEXT_SLOT"]=193L;g["MIX_NEXT_X1"]=g("EX1") or (g("EX0") shl 32)
        }else g["MIX_NEXT_SLOT"]=150L
    }
    private fun round(input:Long,key:Long,bias:Long):Long {
        var b=input xor 0x0f859104ff7029e3L
        var a=(input xor 0x13245352688a24a6L)+b
        var c=a+key
        var d=java.lang.Long.rotateLeft(b,3) xor a
        b=d+bias;a=c xor java.lang.Long.rotateLeft(key,16)
        var sum=a+b
        d=java.lang.Long.rotateLeft(d,30) xor b
        b=java.lang.Long.rotateLeft(a,1) xor sum
        a=d+java.lang.Long.rotateLeft(c,1)
        c=a+b;d=java.lang.Long.rotateLeft(d,3) xor a
        b=java.lang.Long.rotateLeft(b,16) xor c
        a=d+java.lang.Long.rotateLeft(sum,33)
        b=a xor b;sum=java.lang.Long.rotateLeft(d,30) xor a
        return b xor java.lang.Long.rotateLeft(c,1) xor sum
    }
    private fun computeKey():ByteArray {
        call("record_input",g("MIX_FINISHED_X1"),g("MIX_FINISHED_X2"))
        var k=0x97ef94a432921a68UL.toLong()
        var l=0x121066ec1ecc292dL
        var x=g("RECORD_INPUT_X")
        var y=g("RECORD_INPUT_Y")
        for(i in 0 until 32){
            x=(java.lang.Long.rotateRight(x,8)+y) xor k
            y=java.lang.Long.rotateLeft(y,3) xor x
            l=(java.lang.Long.rotateRight(l,8)+k) xor i.toLong()
            k=java.lang.Long.rotateLeft(k,3) xor l
        }
        var left=y
        var right=x
        val keys=longArrayOf(
            0x73e2b993de29abadL,0x0cfdfadcc532b265L,
            0xf3a3a8f312885bacUL.toLong(),0x0d01fd18bccecd8dL,
            0x7327a9f17e888924L,0x0f0602dcaaedbe7dL,
            0xd3e3a9917ef8eb35UL.toLong(),0xed09fd18d4cec184UL.toLong(),
        )
        for(i in keys.indices step 2){
            val next=round(left,keys[i],keys[i+1]) xor right
            right=left;left=next
        }
        return ByteArray(16){i->if(i<8) (left ushr (8*i)).toByte() else (right ushr (8*(i-8))).toByte()}
    }
    fun decode(field:ByteArray):ByteArray {
        responseInput=field
        g.clear()
        call("response_mixing_complete")
        return computeKey()
    }
    private fun call(name:String,vararg args:Long) {
        when(name) {
            "reduced_cbb02c" -> fn_reduced_cbb02c(args)
            "reduced_cbb0b0" -> fn_reduced_cbb0b0(args)
            "reduced_cbb134" -> fn_reduced_cbb134(args)
            "record_input" -> fn_record_input(args)
            "wrapper_run" -> fn_wrapper_run(args)
            "response_cc1558_complete" -> fn_response_cc1558_complete(args)
            "nested_cbf5e0_complete" -> fn_nested_cbf5e0_complete(args)
            "nested_cbf0d8_complete" -> fn_nested_cbf0d8_complete(args)
            "nested_cc1cac_complete" -> fn_nested_cc1cac_complete(args)
            "response_mixing_extended" -> fn_response_mixing_extended(args)
            "response_mixing_preexception" -> fn_response_mixing_preexception(args)
            "throw_cbe868" -> fn_throw_cbe868(args)
            "throw_cc01a8" -> fn_throw_cc01a8(args)
            "throw_cc05f0" -> fn_throw_cc05f0(args)
            "throw_cbe7a8" -> fn_throw_cbe7a8(args)
            "throw_cbc930" -> fn_throw_cbc930(args)
            "response_slot116" -> fn_response_slot116(args)
            "response_slot150" -> fn_response_slot150(args)
            "response_mixing_complete" -> fn_response_mixing_complete(args)
            "wrapper_leaf" -> leaf(args[0].toInt(),args[1],args[2])
            "wrapper_chain" -> chain(*args)
            "wrapper_swapped_leaf" -> swappedLeaf(args[0].toInt())
            "wrapper_optional_leaf" -> optionalLeaf(args[0].toInt(),args[1].toInt())
            "wrapper_swap" -> swap(args[0])
            "wrapper_fold" -> fold()
            "wrapper_repeat" -> repeatLeaf(args[0].toInt(),args[1].toInt(),args[2]!=0L,if(args.size>3)args[3].toInt() else 8)
            "wrapper_optional_prefix" -> optionalPrefix(args[0].toInt(),args[1].toInt())
            "selected_wrapper" -> selected(args[0],args[1],args[2])
            "response_ror32" -> ror32(args[0],args[1])
            "response_table32" -> table32(args[0],args[1])
            "response_mixing_entry" -> responseMixingEntry()
            "response_mixing_exceptions" -> responseMixingExceptions()
            "response_prefix" -> responsePrefix()
            else -> error("Unknown response operation $name")
        }
    }
    private fun fn_reduced_cbb02c(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x"] = 0L
        e["y"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["e"] = 0L
        e["f"] = 0L
        e["g"] = 0L
        e["h"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x", r(e,"1"))
        s(e,"y", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["e"] = 0L
        e["f"] = 0L
        e["g"] = 0L
        e["h"] = 0L
        s(e,"a", (r(e,"y") shr 32L.toInt()))
        s(e,"b", (r(e,"y") and 0xffffffffL))
        s(e,"c", ((r(e,"a") + r(e,"b")) and 0xffffffffL))
        s(e,"d", ((r(e,"x") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", ((((r(e,"c") shr 8L.toInt()) or (r(e,"c") shl 24L.toInt())) xor (r(e,"d") - r(e,"b"))) and 0xffffffffL))
        s(e,"e", ((r(e,"d") shr 28L.toInt()) or (r(e,"d") shl 4L.toInt())))
        s(e,"f", r(e,"x"))
        s(e,"g", ((r(e,"b") shr 11L.toInt()) or (r(e,"b") shl 21L.toInt())))
        s(e,"h", (((((r(e,"c") - r(e,"b")) - r(e,"e")) - r(e,"f")) - r(e,"g")) and 0xffffffffL))
        s(e,"d", (((((r(e,"e") + r(e,"f")) + r(e,"g")) + r(e,"d")) - r(e,"b")) and 0xffffffffL))
        s(e,"a", ((((r(e,"a") - r(e,"e")) - r(e,"f")) - r(e,"g")) xor r(e,"d")))
        s(e,"b", ((((r(e,"b") + r(e,"e")) + r(e,"f")) + r(e,"g")) + r(e,"a")))
        s(e,"e", (r(e,"b") and 0xffffffffL))
        s(e,"c", (r(e,"d") xor r(e,"c")))
        s(e,"a", ((r(e,"c") * 0x9ae6c54L) xor r(e,"a")))
        s(e,"b", ((r(e,"b") - r(e,"a")) and 0xffffffffL))
        s(e,"d", (((r(e,"h") xor r(e,"e")) - ((r(e,"b") shr 11L.toInt()) or (r(e,"b") shl 21L.toInt()))) and 0xffffffffL))
        s(e,"e", (r(e,"e") xor r(e,"c")))
        s(e,"f", ((((r(e,"d") shr 18L.toInt()) or (r(e,"d") shl 14L.toInt())) and 0xffffffffL) xor r(e,"e")))
        s(e,"c", (r(e,"h") xor r(e,"c")))
        s(e,"c", ((r(e,"c") shr 28L.toInt()) or (r(e,"c") shl 4L.toInt())))
        s(e,"b", ((r(e,"b") - r(e,"a")) - r(e,"c")))
        s(e,"g", (r(e,"b") and 0xffffffffL))
        s(e,"a", (((r(e,"c") + r(e,"a")) xor ((r(e,"e") shr 12L.toInt()) or (r(e,"e") shl 20L.toInt()))) and 0xffffffffL))
        s(e,"c", (((((r(e,"g") shr 5L.toInt()) or (r(e,"g") shl 27L.toInt())) + 18022L) xor (r(e,"a") - 14545L)) + r(e,"d")))
        s(e,"O0", r(e,"f"))
        s(e,"O1", (r(e,"c") and 0xffffffffL))
        s(e,"O2", ((r(e,"b") - ((r(e,"a") shr 1L.toInt()) or (r(e,"a") shl 31L.toInt()))) and 0xffffffffL))
        s(e,"O3", (((r(e,"c") + r(e,"f")) and 0xffffffffL) xor r(e,"a")))
    }
    private fun fn_reduced_cbb0b0(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x"] = 0L
        e["y"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["e"] = 0L
        e["f"] = 0L
        e["g"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x", r(e,"1"))
        s(e,"y", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["e"] = 0L
        e["f"] = 0L
        e["g"] = 0L
        s(e,"a", r(e,"y"))
        s(e,"b", (r(e,"x") shr 32L.toInt()))
        s(e,"c", (((r(e,"a") + r(e,"b")) xor r(e,"x")) and 0xffffffffL))
        s(e,"d", (r(e,"y") shr 32L.toInt()))
        s(e,"e", (r(e,"a") + leaves.lookup(((r(e,"d") and 255L) + 433L).toInt())))
        s(e,"a", ((r(e,"b") - r(e,"a")) and 0xffffffffL))
        s(e,"b", (leaves.lookup(((r(e,"e") and 255L) + 442L).toInt()) xor r(e,"a")))
        s(e,"f", (-((r(e,"a") shr 22L.toInt()) or (r(e,"a") shl 10L.toInt()))))
        s(e,"g", (-((r(e,"c") shr 22L.toInt()) or (r(e,"c") shl 10L.toInt()))))
        s(e,"O0", ((r(e,"c") - r(e,"a")) and 0xffffffffL))
        s(e,"O1", r(e,"b"))
        s(e,"O2", ((((r(e,"e") + r(e,"d")) + r(e,"f")) + r(e,"g")) and 0xffffffffL))
        s(e,"O3", ((((((r(e,"c") + r(e,"b")) + r(e,"g")) + r(e,"f")) - r(e,"a")) + r(e,"d")) and 0xffffffffL))
    }
    private fun fn_reduced_cbb134(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x"] = 0L
        e["y"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["e"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x", r(e,"1"))
        s(e,"y", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["e"] = 0L
        s(e,"a", (r(e,"x") and 0xffffffffL))
        s(e,"b", (r(e,"y") and 0xffffffffL))
        s(e,"c", ((r(e,"x") shr 32L.toInt()) - ((r(e,"b") shr 21L.toInt()) or (r(e,"b") shl 11L.toInt()))))
        s(e,"d", (r(e,"a") xor ((r(e,"y") shr 32L.toInt()) and 0xffffffffL)))
        s(e,"b", ((((r(e,"d") shr 28L.toInt()) or (r(e,"d") shl 4L.toInt())) and 0xffffffffL) xor r(e,"b")))
        s(e,"d", ((r(e,"c") + 0x82a561a4L) xor r(e,"d")))
        s(e,"c", ((r(e,"c") - ((((r(e,"b") shr 16L.toInt()) or (r(e,"b") shl 16L.toInt())) + 3450L) xor (r(e,"d") - 23999L))) and 0xffffffffL))
        s(e,"b", ((r(e,"b") - r(e,"d")) and 0xffffffffL))
        s(e,"e", (r(e,"c") xor r(e,"b")))
        s(e,"c", ((r(e,"d") - ((r(e,"b") shr 1L.toInt()) or (r(e,"b") shl 31L.toInt()))) - ((r(e,"c") shr 13L.toInt()) or (r(e,"c") shl 19L.toInt()))))
        s(e,"b", ((r(e,"c") and 0xffffffffL) xor r(e,"b")))
        s(e,"c", (r(e,"c") - ((r(e,"b") + 26447L) xor (((r(e,"e") shr 7L.toInt()) or (r(e,"e") shl 25L.toInt())) + 13892L))))
        s(e,"d", (r(e,"e") - ((r(e,"c") - 10012L) xor (((r(e,"b") shr 29L.toInt()) or (r(e,"b") shl 3L.toInt())) - 6866L))))
        s(e,"e", (r(e,"d") and 0xffffffffL))
        s(e,"d", ((((r(e,"e") shr 14L.toInt()) or (r(e,"e") shl 18L.toInt())) xor r(e,"c")) + r(e,"d")))
        s(e,"O0", r(e,"a"))
        s(e,"O1", (((r(e,"c") + r(e,"e")) - r(e,"b")) and 0xffffffffL))
        s(e,"O2", (r(e,"d") and 0xffffffffL))
        s(e,"O3", (((r(e,"c") + r(e,"d")) - r(e,"b")) and 0xffffffffL))
    }
    private fun fn_record_input(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["u"] = 0L
        e["v"] = 0L
        e["r"] = 0L
        e["s"] = 0L
        e["t"] = 0L
        e["q"] = 0L
        e["h"] = 0L
        e["e"] = 0L
        e["f"] = 0L
        e["w"] = 0L
        e["z"] = 0L
        e["k"] = 0L
        e["p"] = 0L
        e["rr"] = 0L
        e["pr"] = 0L
        e["ar"] = 0L
        e["ur"] = 0L
        e["state_a"] = 0L
        e["state_c"] = 0L
        e["state_d"] = 0L
        e["state_e"] = 0L
        e["n"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"a", (r(e,"1") and 0xffffffffL))
        s(e,"b", ((r(e,"1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"2") and 0xffffffffL))
        s(e,"d", ((r(e,"2") shr 32L.toInt()) and 0xffffffffL))
        e["u"] = 0L
        e["v"] = 0L
        e["r"] = 0L
        e["s"] = 0L
        e["t"] = 0L
        e["q"] = 0L
        e["h"] = 0L
        e["e"] = 0L
        e["f"] = 0L
        e["w"] = 0L
        e["z"] = 0L
        e["k"] = 0L
        e["p"] = 0L
        e["rr"] = 0L
        e["pr"] = 0L
        e["ar"] = 0L
        e["ur"] = 0L
        e["state_a"] = 0L
        e["state_c"] = 0L
        e["state_d"] = 0L
        e["state_e"] = 0L
        e["n"] = 0L
        s(e,"u", ((r(e,"c") - r(e,"d")) and 0xffffffffL))
        s(e,"v", ((((r(e,"u") + (r(e,"a") * 0x4f3f8e14L)) + r(e,"b")) + leaves.lookup((9L + (((((2L * r(e,"c")) - r(e,"d")) + (21L * r(e,"a"))) + r(e,"b")) and 255L)).toInt())) and 0xffffffffL))
        call("response_ror32", r(e,"v"), 28L)
        s(e,"r", (r(e,"ROR_RESULT") xor (((r(e,"u") + (r(e,"a") * 0x4f3f8e15L)) + r(e,"b")) and 0xffffffffL)))
        s(e,"s", (((((r(e,"a") + r(e,"c")) + r(e,"b")) - r(e,"r")) + 0x95a645aaL) and 0xffffffffL))
        call("response_ror32", r(e,"s"), 5L)
        s(e,"t", ((((((((r(e,"ROR_RESULT") + 19187L) and 0xffffffffL) xor ((r(e,"r") + 32370L) and 0xffffffffL)) + (2L * r(e,"c"))) - r(e,"d")) + (r(e,"a") * 0x4f3f8e15L)) + r(e,"b")) and 0xffffffffL))
        call("response_ror32", r(e,"t"), 4L)
        s(e,"q", (r(e,"ROR_RESULT") xor r(e,"v")))
        s(e,"h", ((r(e,"r") + leaves.lookup((423L + (r(e,"q") and 255L)).toInt())) and 0xffffffffL))
        if ((r(e,"h") and 64L) != 0L) {
            call("response_ror32", r(e,"t"), 6L)
            s(e,"e", (r(e,"s") - r(e,"ROR_RESULT")))
            call("response_ror32", r(e,"q"), 14L)
            s(e,"e", ((r(e,"e") - r(e,"ROR_RESULT")) and 0xffffffffL))
            call("response_ror32", r(e,"e"), 1L)
            s(e,"f", ((((r(e,"q") + 30139L) and 0xffffffffL) xor r(e,"t")) xor ((r(e,"ROR_RESULT") + 11696L) and 0xffffffffL)))
            call("selected_wrapper", ((r(e,"f") + 0xfb306605L) and 0xffffffffL), (r(e,"f") or (r(e,"e") shl 32L.toInt())), (r(e,"h") or 0xd31306e500000000UL.toLong()))
            s(e,"v", (r(e,"WR_X0") and 0xffffffffL))
            s(e,"e", ((((r(e,"e") - r(e,"q")) + r(e,"v")) + 0x93e4f0a5L) and 0xffffffffL))
            s(e,"q", ((r(e,"q") - r(e,"v")) and 0xffffffffL))
            call("response_ror32", r(e,"e"), 9L)
            s(e,"t", ((((r(e,"q") - 31456L) and 0xffffffffL) xor r(e,"f")) xor ((r(e,"ROR_RESULT") + 16560L) and 0xffffffffL)))
            s(e,"q", (r(e,"t") xor r(e,"q")))
            s(e,"s", (r(e,"e") xor r(e,"h")))
        }
        call("response_ror32", r(e,"q"), 23L)
        s(e,"v", (r(e,"ROR_RESULT") xor r(e,"s")))
        s(e,"u", ((r(e,"t") - r(e,"v")) and 0xffffffffL))
        call("response_ror32", r(e,"u"), 30L)
        s(e,"w", ((((r(e,"v") - 14916L) and 0xffffffffL) xor r(e,"h")) xor ((r(e,"ROR_RESULT") + 30661L) and 0xffffffffL)))
        s(e,"z", (leaves.lookup((132L + (r(e,"w") and 255L)).toInt()) xor r(e,"q")))
        s(e,"v", (r(e,"z") xor r(e,"v")))
        s(e,"w", (r(e,"w") xor ((r(e,"u") - r(e,"v")) and 0xffffffffL)))
        s(e,"z", (r(e,"w") xor r(e,"z")))
        s(e,"k", (((r(e,"v") + r(e,"z")) + 0xc334e9c7L) and 0xffffffffL))
        call("response_ror32", r(e,"k"), 31L)
        s(e,"rr", r(e,"ROR_RESULT"))
        s(e,"p", ((((r(e,"u") - (2L * r(e,"v"))) - r(e,"z")) + 0x3ccb1639L) and 0xffffffffL))
        call("response_ror32", r(e,"p"), 24L)
        s(e,"pr", r(e,"ROR_RESULT"))
        s(e,"a", (((((((2L * r(e,"z")) - r(e,"rr")) - r(e,"w")) - r(e,"pr")) + r(e,"v")) + 0xfacb6cf0L) and 0xffffffffL))
        call("response_ror32", r(e,"a"), 31L)
        s(e,"ar", r(e,"ROR_RESULT"))
        s(e,"b", (((((((r(e,"u") - r(e,"v")) + (2L * r(e,"z"))) - (2L * r(e,"rr"))) - (2L * r(e,"w"))) - (2L * r(e,"pr"))) + 0x6f2d0652L) and 0xffffffffL))
        call("response_ror32", r(e,"b"), 26L)
        s(e,"ur", r(e,"ROR_RESULT"))
        s(e,"c", leaves.lookup((195L + ((((r(e,"ar") + r(e,"ur")) + r(e,"z")) + 41L) and 255L)).toInt()))
        s(e,"state_a", ((r(e,"a") - r(e,"c")) and 0xffffffffL))
        s(e,"v", (r(e,"b") xor r(e,"state_a")))
        s(e,"state_c", ((((r(e,"ar") + r(e,"ur")) + r(e,"z")) + 932610857L) and 0xffffffffL))
        s(e,"state_d", (((((r(e,"ar") + r(e,"rr")) + r(e,"w")) + r(e,"pr")) + r(e,"ur")) and 0xffffffffL))
        s(e,"state_e", ((r(e,"v") + r(e,"state_a")) and 0xffffffffL))
        s(e,"n", ((r(e,"state_d") - r(e,"state_e")) and 0xffffffffL))
        call("response_ror32", r(e,"n"), 1L)
        s(e,"r", r(e,"ROR_RESULT"))
        s(e,"a", (((r(e,"state_a") - r(e,"state_c")) + r(e,"r")) and 0xffffffffL))
        call("response_ror32", r(e,"a"), 5L)
        s(e,"b", (r(e,"ROR_RESULT") xor r(e,"v")))
        s(e,"c", leaves.lookup((365L + (r(e,"b") and 255L)).toInt()))
        s(e,"d", ((r(e,"n") + r(e,"c")) and 0xffffffffL))
        s(e,"RECORD_INPUT_X", (r(e,"b") or (r(e,"a") shl 32L.toInt())))
        s(e,"RECORD_INPUT_Y", ((r(e,"d") shl 32L.toInt()) or (((r(e,"state_c") - r(e,"r")) and 0xffffffffL) xor ((r(e,"d") * 0x741bac9fL) and 0xffffffffL))))
    }
    private fun fn_wrapper_run(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["entry"] = 0L
        e["first"] = 0L
        e["second"] = 0L
        e["trigger1"] = 0L
        e["trigger2"] = 0L
        e["low"] = 0L
        e["high"] = 0L
        e["residual"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"entry", r(e,"1"))
        s(e,"first", r(e,"2"))
        s(e,"second", r(e,"3"))
        e["trigger1"] = 0L
        e["trigger2"] = 0L
        e["low"] = 0L
        e["high"] = 0L
        e["residual"] = 0L
        s(e,"WR_X0", r(e,"first"))
        s(e,"WR_X1", r(e,"second"))
        when (r(e,"entry")) {
            13383192L -> {
                call("reduced_cbb02c", r(e,"WR_X0"), r(e,"WR_X1"))
                g["PROTECTED_X0"] = r(e,"O0") or (r(e,"O1") shl 32)
                g["PROTECTED_X1"] = r(e,"O2") or (r(e,"O3") shl 32)
                call("reduced_cbb0b0", r(e,"PROTECTED_X0"), r(e,"PROTECTED_X1"))
                g["PROTECTED_X0"] = r(e,"O0") or (r(e,"O1") shl 32)
                g["PROTECTED_X1"] = r(e,"O2") or (r(e,"O3") shl 32)
                s(e,"WR_X0", r(e,"PROTECTED_X0"))
                s(e,"WR_X1", r(e,"PROTECTED_X1"))
            }
            13383252L -> {
                s(e,"trigger1", ((r(e,"first") shr 62L.toInt()) and 1L))
                if (r(e,"trigger1") != 0L) {
                    call("wrapper_swap", r(e,"WR_X0"))
                    call("reduced_cbb134", r(e,"SWAPPED"), r(e,"WR_X1"))
                    g["PROTECTED_X0"] = r(e,"O0") or (r(e,"O1") shl 32)
                    g["PROTECTED_X1"] = r(e,"O2") or (r(e,"O3") shl 32)
                    s(e,"WR_X0", r(e,"PROTECTED_X0"))
                    s(e,"WR_X1", r(e,"PROTECTED_X1"))
                }
                call("wrapper_repeat", 168L, 8L, r(e,"trigger1"))
                call("wrapper_leaf", 49L, r(e,"WR_X0"), r(e,"WR_X1"))
            }
            13383368L -> {
                s(e,"low", (r(e,"first") and 0xffffffffL))
                s(e,"high", ((r(e,"first") shr 32L.toInt()) and 0xffffffffL))
                s(e,"residual", r(e,"first"))
                if (((r(e,"high") shr 21L.toInt()) and 1L) != 0L) {
                    call("wrapper_swapped_leaf", 83L)
                    s(e,"high", (r(e,"WR_X0") and 0xffffffffL))
                    s(e,"low", ((r(e,"WR_X0") shr 32L.toInt()) and 0xffffffffL))
                    s(e,"residual", r(e,"low"))
                }
                if (((r(e,"low") shr 11L.toInt()) and 1L) != 0L) {
                    call("wrapper_leaf", 44L, ((r(e,"high") shl 32L.toInt()) or r(e,"low")), r(e,"WR_X1"))
                    s(e,"high", ((r(e,"WR_X0") shr 32L.toInt()) and 0xffffffffL))
                    s(e,"low", (r(e,"WR_X0") and 0xffffffffL))
                    s(e,"residual", r(e,"low"))
                }
                if (((r(e,"high") shr 31L.toInt()) and 1L) != 0L) {
                    call("wrapper_leaf", 175L, ((r(e,"low") shl 32L.toInt()) or r(e,"high")), r(e,"WR_X1"))
                    s(e,"high", (r(e,"WR_X0") and 0xffffffffL))
                    s(e,"low", ((r(e,"WR_X0") shr 32L.toInt()) and 0xffffffffL))
                    s(e,"residual", r(e,"low"))
                }
                s(e,"WR_X0", (((r(e,"low") xor r(e,"high")) + ((r(e,"WR_X1") and 0xffffffffL) xor ((r(e,"WR_X1") shr 32L.toInt()) and 0xffffffffL))) and 0xffffffffL))
                s(e,"WR_X1", r(e,"residual"))
                return
            }
            13383512L -> {
                call("wrapper_chain", 215L, 261L)
            }
            13383576L -> {
                call("wrapper_repeat", 274L, 18L, 1L)
                call("wrapper_optional_prefix", 45L, 232L)
                call("wrapper_chain", 5L)
            }
            13383684L -> {
                call("wrapper_chain", 224L)
                val pair_1694 = wrapperPair(60L, 255L, 31L, 33L)
                s(e,"trigger1",pair_1694.first); s(e,"trigger2",pair_1694.second)
            }
            13383796L -> {
                call("wrapper_optional_prefix", 58L, 94L)
                call("wrapper_chain", 166L)
            }
            13383864L -> {
                call("wrapper_repeat", 222L, 17L, 1L)
                call("wrapper_chain", 127L)
                call("wrapper_repeat", 90L, 30L, 1L, 4L)
                call("wrapper_chain", 31L)
            }
            13383984L -> {
                call("wrapper_repeat", 273L, 8L, 1L)
                call("wrapper_chain", 107L, 23L)
            }
            13384072L -> {
                call("wrapper_optional_prefix", 57L, 154L)
                call("wrapper_chain", 197L, 164L)
            }
            13384156L -> {
                call("wrapper_optional_prefix", 62L, 241L)
                call("wrapper_chain", 148L, 0L)
            }
            13384232L -> {
                call("wrapper_repeat", 43L, 18L, 1L)
                call("wrapper_chain", 62L, 79L)
            }
            13384336L -> {
                call("wrapper_repeat", 96L, 16L, 1L)
                call("wrapper_repeat", 50L, 4L, 1L)
                call("wrapper_optional_leaf", 52L, 10L)
            }
            13384492L -> {
                call("wrapper_chain", 101L, 13L)
                call("wrapper_repeat", 104L, 0L, 1L)
                call("wrapper_repeat", 117L, 29L, 1L)
            }
            13384616L -> {
                call("wrapper_optional_prefix", 52L, 29L)
                call("wrapper_chain", 14L)
                call("wrapper_repeat", 212L, 13L, 1L)
            }
            13384720L -> {
                val pair_2827 = wrapperPair(34L, 99L, 22L, 171L)
                s(e,"trigger1",pair_2827.first); s(e,"trigger2",pair_2827.second)
                call("wrapper_repeat", 219L, 12L, (if ((if (r(e,"trigger1") == 0L) 1L else 0L) != 0L || r(e,"trigger2") != 0L) 1L else 0L))
                call("wrapper_optional_leaf", 34L, 20L)
            }
            13384880L -> {
                val pair_3015 = wrapperPair(38L, 3L, 6L, 37L)
                s(e,"trigger1",pair_3015.first); s(e,"trigger2",pair_3015.second)
                s(e,"residual", r(e,"first"))
                if (r(e,"trigger2") != 0L) {
                    s(e,"residual", (r(e,"WR_X0") and 0xffffffffL))
                } else {
                    if (r(e,"trigger1") != 0L) {
                        s(e,"residual", ((r(e,"WR_X0") shr 32L.toInt()) and 0xffffffffL))
                    }
                }
                call("wrapper_fold")
                s(e,"WR_X1", r(e,"residual"))
                return
            }
            13384980L -> {
                call("wrapper_chain", 179L)
                call("wrapper_optional_prefix", 47L, 67L)
                call("wrapper_chain", 12L)
            }
            13385060L -> {
                val pair_3413 = wrapperPair(49L, 6L, 17L, 15L)
                s(e,"trigger1",pair_3413.first); s(e,"trigger2",pair_3413.second)
                call("wrapper_repeat", 167L, 30L, (if ((if (r(e,"trigger1") == 0L) 1L else 0L) != 0L || r(e,"trigger2") != 0L) 1L else 0L))
            }
            13385204L -> {
                call("wrapper_chain", 183L)
                call("wrapper_repeat", 1L, 20L, 1L)
            }
            13385284L -> {
                call("wrapper_chain", 28L)
                call("wrapper_optional_prefix", 48L, 93L)
                call("wrapper_chain", 120L)
                call("wrapper_optional_leaf", 34L, 191L)
            }
            13385392L -> {
                call("wrapper_chain", 8L, 272L)
                call("wrapper_repeat", 173L, 31L, 1L)
                call("wrapper_chain", 17L)
                call("wrapper_optional_leaf", 45L, 47L)
            }
            13385524L -> {
                call("wrapper_chain", 230L, 256L, 110L)
            }
            13385600L -> {
                call("wrapper_repeat", 118L, 6L, 1L)
                call("wrapper_repeat", 240L, 2L, 1L)
                call("wrapper_chain", 233L)
                call("wrapper_repeat", 129L, 10L, 1L)
                call("wrapper_chain", 11L)
            }
            13385764L -> {
                call("wrapper_chain", 221L)
                call("wrapper_repeat", 56L, 17L, 1L)
                call("wrapper_chain", 147L)
            }
            13385864L -> {
                call("wrapper_chain", 239L, 89L, 140L)
            }
            13385940L -> {
                call("wrapper_repeat", 236L, 4L, 1L)
                val pair_4459 = wrapperPair(53L, 209L, 8L, 24L)
                s(e,"trigger1",pair_4459.first); s(e,"trigger2",pair_4459.second)
            }
            13386068L -> {
                call("wrapper_optional_prefix", 47L, 55L)
                call("wrapper_chain", 71L)
            }
            13386136L -> {
                call("wrapper_repeat", 109L, 16L, 1L)
                call("wrapper_repeat", 211L, 20L, 1L)
                call("wrapper_repeat", 46L, 30L, 1L)
            }
            13386288L -> {
                call("wrapper_repeat", 270L, 7L, 1L)
                call("wrapper_chain", 228L)
                val pair_4840 = wrapperPair(63L, 234L, 10L, 72L)
                s(e,"trigger1",pair_4840.first); s(e,"trigger2",pair_4840.second)
            }
            13386428L -> {
                call("wrapper_chain", 262L, 32L)
            }
            13386488L -> {
                call("wrapper_chain", 251L)
                call("wrapper_optional_leaf", 41L, 106L)
            }
            13386560L -> {
                call("wrapper_chain", 204L, 207L)
            }
            13386624L -> {
                call("wrapper_chain", 139L, 27L)
                call("wrapper_optional_leaf", 63L, 180L)
            }
            13386700L -> {
                call("wrapper_chain", 70L)
                call("wrapper_repeat", 85L, 7L, 1L)
                call("wrapper_optional_leaf", 56L, 264L)
            }
            13386800L -> {
                call("wrapper_chain", 203L, 165L, 216L)
                call("wrapper_repeat", 82L, 10L, 1L)
            }
            13386920L -> {
                call("wrapper_chain", 80L, 200L)
                call("wrapper_repeat", 103L, 22L, 1L)
            }
            13387016L -> {
                call("wrapper_repeat", 86L, 29L, 1L)
                call("wrapper_repeat", 73L, 3L, 1L)
                call("wrapper_chain", 153L)
            }
            13387136L -> {
                s(e,"trigger1", ((r(e,"WR_X0") shr 47L.toInt()) and 1L))
                if (r(e,"trigger1") != 0L) {
                    call("wrapper_swapped_leaf", 111L)
                }
                call("wrapper_repeat", 4L, 29L, r(e,"trigger1"))
                call("wrapper_chain", 95L)
            }
            13387240L -> {
                call("wrapper_repeat", 41L, 26L, 1L)
                call("wrapper_optional_prefix", 50L, 92L)
                call("wrapper_chain", 145L)
                call("wrapper_optional_leaf", 33L, 74L)
            }
            13387364L -> {
                call("wrapper_chain", 57L, 68L)
                call("wrapper_optional_leaf", 40L, 156L)
            }
            13387452L -> {
                call("wrapper_chain", 69L)
                call("wrapper_repeat", 188L, 14L, 1L)
                call("wrapper_repeat", 249L, 6L, 1L)
            }
            13387564L -> {
                call("wrapper_chain", 259L)
                call("wrapper_repeat", 108L, 10L, 1L)
                call("wrapper_chain", 208L)
            }
            13387664L -> {
                call("wrapper_repeat", 195L, 7L, 1L)
                call("wrapper_chain", 192L)
                call("wrapper_repeat", 124L, 17L, 1L)
            }
            13387792L -> {
                call("wrapper_optional_prefix", 33L, 48L)
                call("wrapper_chain", 227L)
                call("wrapper_optional_leaf", 40L, 115L)
            }
            13387888L -> {
                call("wrapper_optional_prefix", 52L, 75L)
                call("wrapper_chain", 187L, 119L, 238L)
            }
            13387988L -> {
                call("wrapper_repeat", 114L, 12L, 1L)
                call("wrapper_repeat", 105L, 2L, 1L)
            }
            13388120L -> {
                call("wrapper_optional_prefix", 50L, 223L)
                call("wrapper_chain", 198L)
            }
            13388172L -> {
                call("wrapper_chain", 231L, 268L)
                call("wrapper_repeat", 63L, 5L, 1L)
            }
            13388268L -> {
                call("wrapper_optional_prefix", 45L, 45L)
                call("wrapper_chain", 162L, 26L)
            }
            13388352L -> {
                call("wrapper_chain", 52L, 87L, 132L)
            }
            13388428L -> {
                call("wrapper_chain", 9L, 18L)
            }
            13388492L -> {
                call("wrapper_repeat", 237L, 1L, 1L)
                call("wrapper_optional_leaf", 44L, 152L)
            }
            13388580L -> {
                s(e,"trigger1", ((r(e,"WR_X0") shr 56L.toInt()) and 1L))
                if (r(e,"trigger1") != 0L) {
                    call("wrapper_swapped_leaf", 102L)
                }
                call("wrapper_repeat", 253L, 24L, r(e,"trigger1"))
                call("wrapper_optional_leaf", 37L, 265L)
            }
            13388696L -> {
                call("wrapper_chain", 53L)
                call("wrapper_optional_leaf", 51L, 100L)
            }
            13388768L -> {
                call("wrapper_chain", 39L, 246L, 190L)
            }
            13388848L -> {
                call("wrapper_repeat", 170L, 0L, 1L)
                call("wrapper_chain", 138L)
            }
            13388928L -> {
                call("wrapper_repeat", 189L, 5L, 1L)
                call("wrapper_chain", 134L, 38L)
            }
            13389024L -> {
                call("wrapper_chain", 185L)
                call("wrapper_optional_leaf", 58L, 64L)
            }
            13389096L -> {
                call("wrapper_chain", 137L)
                call("wrapper_repeat", 128L, 30L, 1L)
            }
            13389172L -> {
                call("wrapper_chain", 98L, 60L, 77L)
                call("wrapper_optional_prefix", 43L, 21L)
                call("wrapper_chain", 213L)
            }
            13389284L -> {
                call("wrapper_repeat", 243L, 12L, 1L)
                s(e,"trigger1", ((r(e,"WR_X0") shr 58L.toInt()) and 1L))
                if (r(e,"trigger1") != 0L) {
                    call("wrapper_swapped_leaf", 247L)
                }
                call("wrapper_repeat", 40L, 27L, r(e,"trigger1"))
                call("wrapper_chain", 163L, 201L)
            }
            13389432L -> {
                call("wrapper_repeat", 177L, 30L, 1L)
                call("wrapper_optional_prefix", 58L, 218L)
                call("wrapper_chain", 66L)
            }
            13389532L -> {
                call("wrapper_chain", 25L, 202L)
            }
        }
        call("wrapper_fold")
    }
    private fun fn_response_cc1558_complete(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x"] = 0L
        e["y"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["t"] = 0L
        e["u"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x", r(e,"1"))
        s(e,"y", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["t"] = 0L
        e["u"] = 0L
        s(e,"a", ((r(e,"x") shr 32L.toInt()) and 0xffffffffL))
        s(e,"b", (((r(e,"y") shr 32L.toInt()) - r(e,"x")) and 0xffffffffL))
        s(e,"c", ((r(e,"y") - r(e,"b")) and 0xffffffffL))
        s(e,"t", ((r(e,"a") - r(e,"c")) and 0xffffffffL))
        s(e,"d", (((((r(e,"t") shr 13L.toInt()) or (r(e,"t") shl 19L.toInt())) + r(e,"c")) xor r(e,"x")) and 0xffffffffL))
        call("selected_wrapper", ((r(e,"d") + 675191532L) and 0xffffffffL), ((r(e,"t") shl 32L.toInt()) or r(e,"d")), (0xe8a1d80c00000000UL.toLong() or r(e,"c")))
        s(e,"t", (r(e,"WR_X0") xor r(e,"b")))
        s(e,"u", ((((r(e,"d") shr 10L.toInt()) or (r(e,"d") shl 22L.toInt())) + ((r(e,"t") shr 12L.toInt()) or (r(e,"t") shl 20L.toInt()))) and 0xffffffffL))
        s(e,"a", ((r(e,"a") - r(e,"u")) and 0xffffffffL))
        s(e,"u", ((r(e,"c") - r(e,"u")) and 0xffffffffL))
        s(e,"b", (((r(e,"a") * 0x3e249231L) + r(e,"d")) and 0xffffffffL))
        s(e,"c", ((r(e,"t") - r(e,"b")) and 0xffffffffL))
        s(e,"d", (r(e,"c") xor r(e,"u")))
        call("selected_wrapper", ((r(e,"d") + 912666244L) and 0xffffffffL), ((r(e,"c") shl 32L.toInt()) or r(e,"d")), (0x650b4d1c00000000L or r(e,"b")))
        s(e,"t", ((r(e,"a") - r(e,"WR_X0")) and 0xffffffffL))
        s(e,"u", ((r(e,"b") + r(e,"t")) and 0xffffffffL))
        s(e,"c", ((r(e,"c") - r(e,"u")) and 0xffffffffL))
        s(e,"d", (((r(e,"c") + r(e,"d")) + 415868188L) and 0xffffffffL))
        s(e,"t", (((r(e,"t") - ((r(e,"c") shr 6L.toInt()) or (r(e,"c") shl 26L.toInt()))) - ((r(e,"d") shr 28L.toInt()) or (r(e,"d") shl 4L.toInt()))) and 0xffffffffL))
        s(e,"CC_RESULT_X0", ((r(e,"t") shl 32L.toInt()) or ((r(e,"t") + r(e,"u")) and 0xffffffffL)))
        s(e,"CC_RESULT_X1", ((r(e,"c") shl 32L.toInt()) or r(e,"d")))
    }
    private fun fn_nested_cbf5e0_complete(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w13"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w13"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        s(e,"w9", ((r(e,"d") - r(e,"b")) and 0xffffffffL))
        call("response_table32", 0x2ccL, (r(e,"w9") + 157L))
        s(e,"w10", ((r(e,"TABLE_RESULT") + r(e,"c")) and 0xffffffffL))
        s(e,"w8", ((r(e,"b") + 0x80e1463L) and 0xffffffffL))
        s(e,"w11", (((r(e,"w8") - r(e,"w9")) + 549781394L) and 0xffffffffL))
        s(e,"w9", (((r(e,"w8") - r(e,"w9")) - r(e,"w10")) and 0xffffffffL))
        s(e,"w13", ((r(e,"w10") + 684921845L) and 0xffffffffL))
        s(e,"w8", (((r(e,"w13") + r(e,"w10")) - r(e,"w8")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w8") + r(e,"w9")) and 0xffffffffL))
        call("response_ror32", r(e,"w11"), 8L)
        s(e,"w8", ((r(e,"w8") - r(e,"ROR_RESULT")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w8")) and 0xffffffffL))
        call("response_ror32", r(e,"w9"), 23L)
        s(e,"w21", ((r(e,"w11") - r(e,"ROR_RESULT")) and 0xffffffffL))
        s(e,"w22", ((r(e,"w21") + r(e,"w8")) and 0xffffffffL))
        s(e,"w23", (r(e,"w22") xor r(e,"w9")))
        call("selected_wrapper", ((r(e,"w23") + 0xdfd974f7L) and 0xffffffffL), ((r(e,"w22") shl 32L.toInt()) or r(e,"w23")), (0x4179e55e00000000L or r(e,"a")))
        s(e,"w9", ((r(e,"w21") - (r(e,"WR_X0") and 0xffffffffL)) and 0xffffffffL))
        s(e,"w10", (r(e,"w22") xor r(e,"w9")))
        call("response_table32", 0x7a4L, r(e,"w10"))
        s(e,"w8", ((r(e,"w23") - r(e,"TABLE_RESULT")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w8")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w10") - r(e,"w9")) and 0xffffffffL))
        s(e,"w9", (r(e,"w9") xor r(e,"a")))
        call("response_ror32", r(e,"w10"), 21L)
        s(e,"w8", ((r(e,"ROR_RESULT") + r(e,"w8")) and 0xffffffffL))
        s(e,"NEST_X0", ((r(e,"w8") shl 32L.toInt()) or r(e,"a")))
        s(e,"NEST_X1", ((r(e,"w9") shl 32L.toInt()) or r(e,"w10")))
    }
    private fun fn_nested_cbf0d8_complete(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["r1"] = 0L
        e["r2"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["r1"] = 0L
        e["r2"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        s(e,"w21", (((r(e,"d") - r(e,"a")) + 0x42ef069cL) and 0xffffffffL))
        call("response_ror32", r(e,"w21"), 9L)
        s(e,"r1", r(e,"ROR_RESULT"))
        call("response_ror32", r(e,"a"), 11L)
        s(e,"r2", r(e,"ROR_RESULT"))
        s(e,"w22", (((r(e,"r1") + r(e,"r2")) and 0xffffffffL) xor r(e,"c")))
        s(e,"w8", ((r(e,"b") + r(e,"w22")) and 0xffffffffL))
        s(e,"w23", ((r(e,"w8") - 0x49595569L) and 0xffffffffL))
        call("selected_wrapper", ((r(e,"w8") + 0xa9cafcc6L) and 0xffffffffL), ((r(e,"w22") shl 32L.toInt()) or r(e,"w23")), ((-0x75dd8c5100000000L) or r(e,"w21")))
        s(e,"w8", ((r(e,"WR_X0") and 0xffffffffL) xor r(e,"a")))
        s(e,"w9", ((r(e,"w8") + r(e,"w21")) and 0xffffffffL))
        s(e,"w10", (r(e,"w9") xor r(e,"w22")))
        s(e,"w11", ((r(e,"w10") + r(e,"w23")) and 0xffffffffL))
        s(e,"w8", ((r(e,"w11") + r(e,"w8")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w8") + r(e,"w9")) and 0xffffffffL))
        s(e,"w10", (((r(e,"w10") - r(e,"w8")) - r(e,"w9")) and 0xffffffffL))
        call("response_ror32", r(e,"w10"), 5L)
        s(e,"w12", (((r(e,"ROR_RESULT") + 23741L) and 0xffffffffL) xor ((r(e,"w9") + 8271L) and 0xffffffffL)))
        s(e,"w11", ((r(e,"w11") - r(e,"w12")) and 0xffffffffL))
        s(e,"w8", (r(e,"w8") xor r(e,"w11")))
        call("response_table32", 0x7ccL, r(e,"w8"))
        s(e,"w9", ((r(e,"w9") + r(e,"TABLE_RESULT")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w10") - r(e,"w9")) and 0xffffffffL))
        s(e,"w11", ((r(e,"w11") + (r(e,"w10") * 0x809436ebL)) and 0xffffffffL))
        s(e,"w8", (((r(e,"w8") - r(e,"w11")) + 0x947ae2b7L) and 0xffffffffL))
        s(e,"NEST_X0", ((r(e,"w11") shl 32L.toInt()) or r(e,"w8")))
        s(e,"NEST_X1", ((r(e,"w9") shl 32L.toInt()) or r(e,"w10")))
    }
    private fun fn_nested_cc1cac_complete(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["lookup"] = 0L
        e["r1"] = 0L
        e["r2"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["lookup"] = 0L
        e["r1"] = 0L
        e["r2"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        call("response_ror32", r(e,"a"), 5L)
        s(e,"w8", ((r(e,"d") - r(e,"ROR_RESULT")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w8") + r(e,"c")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w9") + r(e,"b")) and 0xffffffffL))
        call("response_ror32", r(e,"w10"), 6L)
        s(e,"w20", ((r(e,"a") - r(e,"ROR_RESULT")) and 0xffffffffL))
        s(e,"w8", ((r(e,"w8") - r(e,"w20")) and 0xffffffffL))
        s(e,"w21", ((r(e,"w8") + 0xeac46f06L) and 0xffffffffL))
        call("response_table32", 0x4f4L, r(e,"w21"))
        s(e,"w22", (r(e,"TABLE_RESULT") xor r(e,"w9")))
        s(e,"w23", ((r(e,"w10") - r(e,"w22")) and 0xffffffffL))
        call("selected_wrapper", ((r(e,"w23") + 0x577a834dL) and 0xffffffffL), ((r(e,"w22") shl 32L.toInt()) or r(e,"w23")), ((-0x5bb3b8af00000000L) or r(e,"w21")))
        s(e,"w8", ((r(e,"w21") - r(e,"w23")) and 0xffffffffL))
        s(e,"w9", ((r(e,"WR_X0") and 0xffffffffL) xor r(e,"w20")))
        s(e,"w8", ((r(e,"w8") - r(e,"w9")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w22") - r(e,"w8")) and 0xffffffffL))
        s(e,"w11", (((r(e,"w10") * 0x48529b6bL) and 0xffffffffL) xor r(e,"w23")))
        call("response_ror32", r(e,"w11"), 29L)
        s(e,"r1", r(e,"ROR_RESULT"))
        call("response_ror32", r(e,"w10"), 1L)
        s(e,"r2", r(e,"ROR_RESULT"))
        s(e,"w10", ((r(e,"r1") + r(e,"r2")) and 0xffffffffL))
        s(e,"w9", (r(e,"w9") xor r(e,"w10")))
        call("response_table32", 0x1fcL, r(e,"w9"))
        s(e,"lookup", r(e,"TABLE_RESULT"))
        s(e,"w12", ((r(e,"lookup") + r(e,"w22")) and 0xffffffffL))
        s(e,"w8", ((r(e,"w8") + r(e,"lookup")) and 0xffffffffL))
        s(e,"w11", ((r(e,"w11") - r(e,"w12")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w11")) and 0xffffffffL))
        s(e,"w8", ((r(e,"w8") - r(e,"w9")) and 0xffffffffL))
        call("response_ror32", r(e,"w8"), 18L)
        s(e,"r1", ((r(e,"ROR_RESULT") - 18135L) and 0xffffffffL))
        s(e,"r2", ((r(e,"w9") - 9080L) and 0xffffffffL))
        s(e,"w10", ((r(e,"w12") - (r(e,"r1") xor r(e,"r2"))) and 0xffffffffL))
        call("response_ror32", r(e,"w10"), 19L)
        s(e,"r1", r(e,"ROR_RESULT"))
        call("response_ror32", r(e,"w8"), 7L)
        s(e,"r2", r(e,"ROR_RESULT"))
        s(e,"w11", (r(e,"w11") xor ((r(e,"r1") + r(e,"r2")) and 0xffffffffL)))
        s(e,"w9", (r(e,"w9") xor ((r(e,"w11") + r(e,"w10")) and 0xffffffffL)))
        call("response_ror32", r(e,"w11"), 16L)
        s(e,"r1", r(e,"ROR_RESULT"))
        call("response_ror32", r(e,"w9"), 20L)
        s(e,"r2", r(e,"ROR_RESULT"))
        s(e,"w8", (((r(e,"w8") - r(e,"r1")) - r(e,"r2")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w8") + r(e,"w10")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w10")) and 0xffffffffL))
        call("response_ror32", r(e,"w10"), 16L)
        s(e,"r1", ((r(e,"ROR_RESULT") + 4399L) and 0xffffffffL))
        s(e,"r2", ((r(e,"w8") - 30881L) and 0xffffffffL))
        s(e,"w11", ((r(e,"w11") + (r(e,"r1") xor r(e,"r2"))) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w11")) and 0xffffffffL))
        s(e,"NEST_X0", ((r(e,"w11") shl 32L.toInt()) or r(e,"w9")))
        s(e,"NEST_X1", ((r(e,"w8") shl 32L.toInt()) or r(e,"w10")))
    }
    private fun fn_response_mixing_extended(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["hex"] = 0L
        e["first"] = 0L
        e["second"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"hex", r(e,"1"))
        e["first"] = 0L
        e["second"] = 0L
        responseMixingEntry()
        call("nested_cbf0d8_complete", r(e,"MIX_NEXT_X1"), r(e,"MIX_NEXT_X2"))
        s(e,"first", r(e,"NEST_X0"))
        s(e,"second", r(e,"NEST_X1"))
        if ((r(e,"first") and (1L shl 11L.toInt())) != 0L) {
            call("wrapper_leaf", 158L, r(e,"first"), r(e,"second"))
            s(e,"first", r(e,"WR_X0"))
            s(e,"second", r(e,"WR_X1"))
        }
        call("wrapper_swap", r(e,"first"))
        call("wrapper_leaf", 257L, r(e,"SWAPPED"), r(e,"second"))
        call("nested_cc1cac_complete", r(e,"WR_X0"), r(e,"WR_X1"))
        s(e,"MIX_NEXT_SLOT", 84L)
        s(e,"MIX_NEXT_X1", r(e,"NEST_X0"))
        s(e,"MIX_NEXT_X2", r(e,"NEST_X1"))
    }
    private fun fn_response_mixing_preexception(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["hex"] = 0L
        e["first"] = 0L
        e["second"] = 0L
        e["count"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"hex", r(e,"1"))
        e["first"] = 0L
        e["second"] = 0L
        e["count"] = 0L
        call("response_mixing_extended", r(e,"hex"))
        s(e,"first", r(e,"MIX_NEXT_X1"))
        s(e,"second", r(e,"MIX_NEXT_X2"))
        s(e,"count", 0L)
        while ((if (r(e,"count") < 8L) 1L else 0L) != 0L) {
            call("nested_cbf5e0_complete", r(e,"first"), r(e,"second"))
            s(e,"first", r(e,"NEST_X0"))
            s(e,"second", r(e,"NEST_X1"))
            if ((if (r(e,"count") >= ((r(e,"first") shr 26L.toInt()) and 7L)) 1L else 0L) != 0L) {
                break
            }
            s(e,"count",r(e,"count") + 1L)
        }
        (if (r(e,"count") < 8L) 1L else 0L) // expression
        call("wrapper_swap", r(e,"first"))
        s(e,"MIX_NEXT_SLOT", 194L)
        s(e,"MIX_NEXT_X1", r(e,"SWAPPED"))
        s(e,"MIX_NEXT_X2", r(e,"second"))
    }
    private fun fn_throw_cbe868(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["v"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w19"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["v"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w19"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        s(e,"w8", (leaves.lookup((268L + (r(e,"a") and 255L)).toInt()) xor r(e,"d")))
        s(e,"w10", (((r(e,"c") - (((r(e,"a") shr 16L.toInt()) or (r(e,"a") shl 16L.toInt())) and 0xffffffffL)) - (((r(e,"w8") shr 2L.toInt()) or (r(e,"w8") shl 30L.toInt())) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w11", (r(e,"w10") xor r(e,"b")))
        s(e,"w20", (((r(e,"w11") * 0x7cb83902L) and 0xffffffffL) xor r(e,"a")))
        s(e,"w21", ((r(e,"w8") - r(e,"w20")) and 0xffffffffL))
        s(e,"w22", (((r(e,"w10") + 0xaa5839f5L) + r(e,"w21")) and 0xffffffffL))
        s(e,"w23", ((r(e,"w11") - r(e,"w22")) and 0xffffffffL))
        call("selected_wrapper", ((r(e,"w23") + 0xf0368075L) and 0xffffffffL), (r(e,"w23") or (r(e,"w22") shl 32L.toInt())), (r(e,"w21") or 0x25d0a3b000000000L))
        s(e,"v", (r(e,"WR_X0") and 0xffffffffL))
        s(e,"w20", ((r(e,"w20") + r(e,"v")) and 0xffffffffL))
        s(e,"w21", ((r(e,"w21") + (r(e,"w20") * 0xcb3ec842L)) and 0xffffffffL))
        call("selected_wrapper", ((r(e,"w21") + 0x64b115a1L) and 0xffffffffL), (r(e,"w21") or (r(e,"w20") shl 32L.toInt())), (r(e,"w23") or 0xbbf8180100000000UL.toLong()))
        s(e,"v", (r(e,"WR_X0") and 0xffffffffL))
        s(e,"w9", ((r(e,"w22") + r(e,"v")) and 0xffffffffL))
        s(e,"w10", (((r(e,"w9") + r(e,"w21")) and 0xffffffffL) xor r(e,"w23")))
        s(e,"w8", ((r(e,"w20") + (r(e,"w10") * 0x6ecec79cL)) and 0xffffffffL))
        s(e,"w20", ((r(e,"w21") - r(e,"w8")) and 0xffffffffL))
        s(e,"w19", ((r(e,"w9") - leaves.lookup((497L + (r(e,"w20") and 255L)).toInt())) and 0xffffffffL))
        s(e,"w21", (r(e,"w10") xor (((r(e,"w19") shr 23L.toInt()) or (r(e,"w19") shl 9L.toInt())) and 0xffffffffL)))
        s(e,"w22", (r(e,"w8") xor ((r(e,"w21") + 0xf91db811L) and 0xffffffffL)))
        s(e,"EX0", r(e,"w22"))
        s(e,"EX1", (r(e,"w21") and 0xffffffffL))
        s(e,"EX2", r(e,"w19"))
        s(e,"EX3", r(e,"w20"))
    }
    private fun fn_throw_cc01a8(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["v"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["w19"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["v"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["w19"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        s(e,"w8", (((r(e,"a") + 0x73cf39e8L) and 0xffffffffL) xor r(e,"d")))
        s(e,"w9", ((r(e,"w8") + r(e,"c")) and 0xffffffffL))
        s(e,"w11", ((leaves.lookup((244L + (r(e,"w9") and 255L)).toInt()) + r(e,"b")) and 0xffffffffL))
        s(e,"w12", (leaves.lookup((195L + (r(e,"w11") and 255L)).toInt()) xor r(e,"a")))
        s(e,"w8", (r(e,"w8") xor r(e,"w12")))
        s(e,"w9", (r(e,"w9") xor r(e,"w8")))
        s(e,"w11", (r(e,"w11") xor ((r(e,"w9") + r(e,"w8")) and 0xffffffffL)))
        s(e,"w12", (r(e,"w12") xor (((r(e,"w11") shr 13L.toInt()) or (r(e,"w11") shl 19L.toInt())) and 0xffffffffL)))
        s(e,"w9", ((r(e,"w9") + r(e,"w12")) and 0xffffffffL))
        s(e,"w8", ((r(e,"w8") - leaves.lookup((299L + (r(e,"w12") and 255L)).toInt())) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") + r(e,"w8")) and 0xffffffffL))
        s(e,"w11", ((r(e,"w9") + r(e,"w11")) and 0xffffffffL))
        s(e,"w12", (((r(e,"w12") - (((r(e,"w9") shr 7L.toInt()) or (r(e,"w9") shl 25L.toInt())) and 0xffffffffL)) - (((r(e,"w11") shr 19L.toInt()) or (r(e,"w11") shl 13L.toInt())) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w8", ((r(e,"w8") - r(e,"w12")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w8") + r(e,"w9")) and 0xffffffffL))
        s(e,"w11", (((r(e,"w8") + r(e,"w11")) + r(e,"w9")) and 0xffffffffL))
        s(e,"w12", (r(e,"w12") xor ((r(e,"w11") + 559643004L) and 0xffffffffL)))
        s(e,"w9", ((r(e,"w12") + r(e,"w9")) and 0xffffffffL))
        s(e,"w8", ((r(e,"w8") - (((r(e,"w12") shr 6L.toInt()) or (r(e,"w12") shl 26L.toInt())) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") + r(e,"w8")) and 0xffffffffL))
        s(e,"w11", ((r(e,"w9") + r(e,"w11")) and 0xffffffffL))
        s(e,"w12", (r(e,"w12") xor ((r(e,"w11") + 0xe0586060L) and 0xffffffffL)))
        s(e,"w19", ((r(e,"w8") - r(e,"w12")) and 0xffffffffL))
        s(e,"w20", ((r(e,"w9") - (((r(e,"w19") shr 28L.toInt()) or (r(e,"w19") shl 4L.toInt())) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w21", ((r(e,"w11") - leaves.lookup((380L + (r(e,"w20") and 255L)).toInt())) and 0xffffffffL))
        s(e,"w22", ((r(e,"w12") - r(e,"w21")) and 0xffffffffL))
        s(e,"EX0", r(e,"w22"))
        s(e,"EX1", r(e,"w21"))
        s(e,"EX2", r(e,"w20"))
        s(e,"EX3", r(e,"w19"))
    }
    private fun fn_throw_cc05f0(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["v"] = 0L
        e["w11"] = 0L
        e["w19"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["w24"] = 0L
        e["w8"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["v"] = 0L
        e["w11"] = 0L
        e["w19"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["w24"] = 0L
        e["w8"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        s(e,"w8", (r(e,"a") xor r(e,"d")))
        s(e,"w20", (leaves.lookup((193L + (r(e,"w8") and 255L)).toInt()) xor r(e,"c")))
        s(e,"w11", (((r(e,"b") - r(e,"w8")) - r(e,"w20")) and 0xffffffffL))
        s(e,"w21", ((r(e,"w11") + r(e,"w20")) and 0xffffffffL))
        s(e,"w11", (r(e,"w11") xor r(e,"a")))
        s(e,"w23", ((r(e,"w8") - r(e,"w11")) and 0xffffffffL))
        s(e,"w22", ((r(e,"w11") - leaves.lookup((23L + (r(e,"w21") and 255L)).toInt())) and 0xffffffffL))
        s(e,"w24", (((r(e,"w22") + 0x49f10213L) and 0xffffffffL) xor r(e,"w23")))
        call("selected_wrapper", ((r(e,"w24") + 0x3d105af3L) and 0xffffffffL), (r(e,"w24") or (r(e,"w22") shl 32L.toInt())), (r(e,"w21") or 0xbbb7393600000000UL.toLong()))
        s(e,"v", (r(e,"WR_X0") and 0xffffffffL))
        s(e,"w20", (((r(e,"w20") - r(e,"w23")) - r(e,"v")) and 0xffffffffL))
        s(e,"w19", (leaves.lookup((382L + (r(e,"w20") and 255L)).toInt()) xor r(e,"w21")))
        s(e,"EX0", ((r(e,"w22") - r(e,"w19")) and 0xffffffffL))
        s(e,"EX1", (r(e,"w19") and 0xffffffffL))
        s(e,"EX2", r(e,"w20"))
        s(e,"EX3", (r(e,"w24") and 0xffffffffL))
    }
    private fun fn_throw_cbe7a8(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["v"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["v"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        s(e,"w9", ((r(e,"d") + r(e,"b")) and 0xffffffffL))
        s(e,"w10", ((r(e,"c") - (((r(e,"w9") shr 2L.toInt()) or (r(e,"w9") shl 30L.toInt())) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w8", ((r(e,"w10") + r(e,"b")) and 0xffffffffL))
        s(e,"w9", (r(e,"w9") xor leaves.lookup((162L + (r(e,"w8") and 255L)).toInt())))
        s(e,"w10", ((r(e,"w10") + (r(e,"w9") * 0x95355a9fL)) and 0xffffffffL))
        s(e,"w11", ((r(e,"w10") + r(e,"w9")) and 0xffffffffL))
        s(e,"w8", (r(e,"w11") xor r(e,"w8")))
        s(e,"w9", (r(e,"w9") xor r(e,"w8")))
        s(e,"w10", ((r(e,"w10") + r(e,"w9")) and 0xffffffffL))
        s(e,"w11", ((((((r(e,"w10") shr 16L.toInt()) or (r(e,"w10") shl 16L.toInt())) and 0xffffffffL) + 8107L) xor (r(e,"w9") - 2874L)) and 0xffffffffL))
        s(e,"w8", ((r(e,"w8") + r(e,"w11")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w8")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w10") + 0x861773eeL) and 0xffffffffL))
        s(e,"w22", (r(e,"w9") xor r(e,"a")))
        s(e,"w20", ((r(e,"w10") + r(e,"w9")) and 0xffffffffL))
        s(e,"w21", ((r(e,"w8") - r(e,"w20")) and 0xffffffffL))
        s(e,"EX0", r(e,"a"))
        s(e,"EX1", r(e,"w21"))
        s(e,"EX2", r(e,"w20"))
        s(e,"EX3", r(e,"w22"))
    }
    private fun fn_throw_cbc930(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["v"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["w19"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["lookup"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["v"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["w19"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["lookup"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        s(e,"w8", (r(e,"d") xor r(e,"a")))
        s(e,"w9", (r(e,"w8") xor r(e,"c")))
        s(e,"w10", ((r(e,"b") - (((r(e,"w9") shr 28L.toInt()) or (r(e,"w9") shl 4L.toInt())) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w11", ((r(e,"a") - (((r(e,"w10") shr 19L.toInt()) or (r(e,"w10") shl 13L.toInt())) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w12", ((((((r(e,"w11") shr 21L.toInt()) or (r(e,"w11") shl 11L.toInt())) and 0xffffffffL) - 6739L) xor (r(e,"w10") - 6863L)) and 0xffffffffL))
        s(e,"w8", ((r(e,"w12") + r(e,"w8")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w10") + 0xd67799e9L) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - (((r(e,"w8") shr 28L.toInt()) or (r(e,"w8") shl 4L.toInt())) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w11", ((r(e,"w11") - leaves.lookup((176L + ((r(e,"w10") - r(e,"w9")) and 255L)).toInt())) and 0xffffffffL))
        s(e,"w8", (r(e,"w8") xor leaves.lookup((425L + (r(e,"w11") and 255L)).toInt())))
        s(e,"lookup", leaves.lookup((378L + (r(e,"w8") and 255L)).toInt()))
        s(e,"w10", ((r(e,"w10") + r(e,"lookup")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") + r(e,"lookup")) and 0xffffffffL))
        s(e,"w11", ((r(e,"w11") + ((((((r(e,"w10") shr 20L.toInt()) or (r(e,"w10") shl 12L.toInt())) and 0xffffffffL) - 30566L) xor (r(e,"w9") - 18716L)) and 0xffffffffL)) and 0xffffffffL))
        s(e,"w20", ((r(e,"w11") + r(e,"w8")) and 0xffffffffL))
        s(e,"w21", ((r(e,"w9") - r(e,"w20")) and 0xffffffffL))
        s(e,"w22", ((((((((r(e,"w21") shr 4L.toInt()) or (r(e,"w21") shl 28L.toInt())) and 0xffffffffL) - 25677L) xor (r(e,"w20") + 8679L)) and 0xffffffffL) + r(e,"w10")) and 0xffffffffL))
        s(e,"w23", (((r(e,"w22") * 679256774L) and 0xffffffffL) xor r(e,"w11")))
        call("selected_wrapper", ((r(e,"w23") + 75544404L) and 0xffffffffL), ((r(e,"w22") shl 32L.toInt()) or r(e,"w23")), (0xfce2632500000000UL.toLong() or r(e,"w21")))
        s(e,"v", (r(e,"WR_X0") and 0xffffffffL))
        s(e,"w9", ((r(e,"w20") + r(e,"v")) and 0xffffffffL))
        s(e,"w10", (r(e,"w21") xor (((r(e,"w9") shr 19L.toInt()) or (r(e,"w9") shl 13L.toInt())) and 0xffffffffL)))
        s(e,"w11", (r(e,"w22") xor r(e,"w10")))
        s(e,"w12", (r(e,"w23") xor leaves.lookup((163L + (r(e,"w11") and 255L)).toInt())))
        s(e,"w19", ((r(e,"w9") - leaves.lookup((63L + (r(e,"w12") and 255L)).toInt())) and 0xffffffffL))
        s(e,"w20", (((r(e,"w12") + r(e,"w19")) and 0xffffffffL) xor r(e,"w10")))
        s(e,"w21", (((((((((r(e,"w20") and 0xffffffffL) shr 2L.toInt()) or ((r(e,"w20") and 0xffffffffL) shl 30L.toInt())) and 0xffffffffL) + 13730L) xor (r(e,"w19") - 29897L)) and 0xffffffffL) + r(e,"w11")) and 0xffffffffL))
        s(e,"w22", (r(e,"w21") xor r(e,"w12")))
        s(e,"EX0", (r(e,"w22") and 0xffffffffL))
        s(e,"EX1", r(e,"w21"))
        s(e,"EX2", (r(e,"w20") and 0xffffffffL))
        s(e,"EX3", r(e,"w19"))
    }
    private fun fn_response_slot116(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w21"] = 0L
        e["w23"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w8"] = 0L
        e["v"] = 0L
        e["rot1"] = 0L
        e["rot2"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w21"] = 0L
        e["w23"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w8"] = 0L
        e["v"] = 0L
        e["rot1"] = 0L
        e["rot2"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        s(e,"w21", (r(e,"b") xor r(e,"d")))
        call("response_ror32", r(e,"w21"), 16L)
        s(e,"w23", ((r(e,"c") - (((r(e,"ROR_RESULT") - 7387L) and 0xffffffffL) xor ((r(e,"b") + 24654L) and 0xffffffffL))) and 0xffffffffL))
        call("selected_wrapper", ((r(e,"w23") + 0xefbe22fbL) and 0xffffffffL), (r(e,"w23") or (r(e,"w21") shl 32L.toInt())), (r(e,"a") or 0x12f7a8a400000000L))
        s(e,"v", (r(e,"WR_X0") and 0xffffffffL))
        s(e,"w9", ((r(e,"v") + r(e,"b")) and 0xffffffffL))
        call("response_ror32", r(e,"w9"), 29L)
        s(e,"w10", ((r(e,"w21") - r(e,"ROR_RESULT")) and 0xffffffffL))
        s(e,"w8", (((r(e,"w23") + 0xe502eb50L) + r(e,"w10")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w10") + 890795887L) and 0xffffffffL))
        s(e,"w9", ((r(e,"w8") + r(e,"w9")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w10") + r(e,"w9")) and 0xffffffffL))
        s(e,"w8", ((r(e,"w8") - r(e,"w10")) and 0xffffffffL))
        call("response_ror32", r(e,"w8"), 5L)
        s(e,"w9", (r(e,"w9") xor r(e,"ROR_RESULT")))
        call("response_table32", 8L, r(e,"w9"))
        s(e,"w10", (r(e,"w10") xor r(e,"TABLE_RESULT")))
        call("response_table32", 0x17cL, r(e,"w10"))
        s(e,"w8", ((r(e,"w8") + r(e,"TABLE_RESULT")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w8")) and 0xffffffffL))
        s(e,"w10", (r(e,"w10") xor ((r(e,"w9") + 0xea595973L) and 0xffffffffL)))
        s(e,"w8", (r(e,"w8") xor ((r(e,"w10") + 0xc7506af7L) and 0xffffffffL)))
        call("response_ror32", r(e,"w10"), 14L)
        s(e,"rot1", r(e,"ROR_RESULT"))
        call("response_ror32", r(e,"w8"), 14L)
        s(e,"rot2", r(e,"ROR_RESULT"))
        s(e,"w9", (((r(e,"w9") - r(e,"rot1")) - r(e,"rot2")) and 0xffffffffL))
        s(e,"w10", (((r(e,"w8") + r(e,"w10")) + r(e,"w9")) and 0xffffffffL))
        s(e,"w8", (r(e,"w8") xor ((r(e,"w10") + 0x8aca6d5L) and 0xffffffffL)))
        s(e,"w10", (r(e,"w10") xor r(e,"a")))
        call("response_table32", 0x6e0L, r(e,"w8"))
        s(e,"w9", ((r(e,"w9") + r(e,"TABLE_RESULT")) and 0xffffffffL))
        s(e,"LAST_X0", (r(e,"a") or (r(e,"w9") shl 32L.toInt())))
        s(e,"LAST_X1", (r(e,"w8") or (r(e,"w10") shl 32L.toInt())))
    }
    private fun fn_response_slot150(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["x1"] = 0L
        e["x2"] = 0L
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w23"] = 0L
        e["w9"] = 0L
        e["w8"] = 0L
        e["w12"] = 0L
        e["w10"] = 0L
        e["w13"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w11"] = 0L
        e["lookup_index"] = 0L
        e["rot1"] = 0L
        e["rot2"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"x1", r(e,"1"))
        s(e,"x2", r(e,"2"))
        e["a"] = 0L
        e["b"] = 0L
        e["c"] = 0L
        e["d"] = 0L
        e["w23"] = 0L
        e["w9"] = 0L
        e["w8"] = 0L
        e["w12"] = 0L
        e["w10"] = 0L
        e["w13"] = 0L
        e["w20"] = 0L
        e["w21"] = 0L
        e["w22"] = 0L
        e["w11"] = 0L
        e["lookup_index"] = 0L
        e["rot1"] = 0L
        e["rot2"] = 0L
        s(e,"a", (r(e,"x1") and 0xffffffffL))
        s(e,"b", ((r(e,"x1") shr 32L.toInt()) and 0xffffffffL))
        s(e,"c", (r(e,"x2") and 0xffffffffL))
        s(e,"d", ((r(e,"x2") shr 32L.toInt()) and 0xffffffffL))
        call("response_ror32", r(e,"a"), 9L)
        s(e,"rot1", r(e,"ROR_RESULT"))
        call("response_ror32", r(e,"b"), 20L)
        s(e,"rot2", r(e,"ROR_RESULT"))
        s(e,"w23", (((r(e,"d") - r(e,"rot1")) - r(e,"rot2")) and 0xffffffffL))
        call("selected_wrapper", ((r(e,"w23") + 0xc9a1f079L) and 0xffffffffL), ((r(e,"a") shl 32L.toInt()) or r(e,"w23")), ((-0x59bafb5d00000000L) or r(e,"b")))
        s(e,"w9", ((r(e,"WR_X0") and 0xffffffffL) xor r(e,"c")))
        call("response_table32", 0x770L, r(e,"w9"))
        s(e,"w8", ((r(e,"TABLE_RESULT") + r(e,"b")) and 0xffffffffL))
        s(e,"w12", ((r(e,"a") - r(e,"w8")) and 0xffffffffL))
        s(e,"w10", (((r(e,"w23") + 0xdc5ef0daL) + r(e,"w12")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w10")) and 0xffffffffL))
        s(e,"w13", ((r(e,"w9") + 0x75703f78L) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") + 0x75702f04L) and 0xffffffffL))
        s(e,"w8", (r(e,"w13") xor r(e,"w8")))
        call("response_ror32", r(e,"w8"), 21L)
        s(e,"w9", (((((r(e,"ROR_RESULT") + 3386L) and 0xffffffffL) xor r(e,"w9")) + r(e,"w12")) and 0xffffffffL))
        call("response_ror32", r(e,"w9"), 28L)
        s(e,"rot1", r(e,"ROR_RESULT"))
        call("response_ror32", r(e,"w8"), 2L)
        s(e,"rot2", r(e,"ROR_RESULT"))
        s(e,"w10", (r(e,"w10") xor ((r(e,"rot1") + r(e,"rot2")) and 0xffffffffL)))
        call("response_ror32", r(e,"w10"), 13L)
        s(e,"w12", (((r(e,"ROR_RESULT") + 6252L) xor (r(e,"w9") - 2923L)) and 0xffffffffL))
        s(e,"w20", ((r(e,"w13") - r(e,"w12")) and 0xffffffffL))
        s(e,"w21", (((r(e,"w20") + 558434014L) and 0xffffffffL) xor r(e,"w8")))
        s(e,"w22", ((r(e,"w9") + (r(e,"w21") * 0xa5001aebL)) and 0xffffffffL))
        s(e,"w23", (((r(e,"w22") + r(e,"w21")) and 0xffffffffL) xor r(e,"w10")))
        call("selected_wrapper", ((r(e,"w23") + 0x6ac38a1dL) and 0xffffffffL), ((r(e,"w22") shl 32L.toInt()) or r(e,"w23")), (0x3ccff0f400000000L or r(e,"w21")))
        s(e,"w9", (r(e,"w20") xor (r(e,"WR_X0") and 0xffffffffL)))
        s(e,"w10", ((r(e,"w21") - r(e,"w9")) and 0xffffffffL))
        s(e,"lookup_index", (r(e,"w22") xor r(e,"w10")))
        call("response_table32", 0x604L, r(e,"lookup_index"))
        s(e,"w11", ((r(e,"w23") - r(e,"TABLE_RESULT")) and 0xffffffffL))
        s(e,"w12", ((r(e,"w11") - r(e,"w22")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w9") - r(e,"w11")) and 0xffffffffL))
        s(e,"w10", ((r(e,"w10") - r(e,"w9")) and 0xffffffffL))
        call("response_table32", 0x158L, r(e,"w12"))
        s(e,"w8", ((r(e,"w9") - r(e,"TABLE_RESULT")) and 0xffffffffL))
        s(e,"w9", (r(e,"w10") xor r(e,"w8")))
        call("response_ror32", r(e,"w9"), 23L)
        s(e,"w10", (r(e,"w22") xor r(e,"ROR_RESULT")))
        s(e,"LAST_X0", ((r(e,"w9") shl 32L.toInt()) or r(e,"w10")))
        s(e,"LAST_X1", ((r(e,"w12") shl 32L.toInt()) or r(e,"w8")))
    }
    private fun fn_response_mixing_complete(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["hex"] = 0L
        e["first"] = 0L
        e["second"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"hex", r(e,"1"))
        e["first"] = 0L
        e["second"] = 0L
        call("response_mixing_exceptions", r(e,"hex"))
        s(e,"first", r(e,"EX_MIX_FIRST"))
        s(e,"second", r(e,"EX_MIX_SECOND"))
        if ((r(e,"first") and (1L shl 11L.toInt())) != 0L) {
            call("response_slot116", r(e,"first"), r(e,"second"))
            s(e,"first", r(e,"LAST_X0"))
            s(e,"second", r(e,"LAST_X1"))
        }
        if ((r(e,"first") and (1L shl 48L.toInt())) != 0L) {
            call("wrapper_swap", r(e,"first"))
            call("wrapper_leaf", 193L, r(e,"SWAPPED"), r(e,"second"))
            call("wrapper_swap", r(e,"WR_X0"))
            s(e,"first", r(e,"SWAPPED"))
            s(e,"second", r(e,"WR_X1"))
        }
        call("response_slot150", r(e,"first"), r(e,"second"))
        s(e,"WR_X0", r(e,"LAST_X0"))
        s(e,"WR_X1", r(e,"LAST_X1"))
        call("wrapper_repeat", 260L, 8L, 0L)
        call("wrapper_repeat", 81L, 20L, 1L)
        if (((r(e,"WR_X0") shr 32L.toInt()) and 1L) != 0L) {
            call("wrapper_swapped_leaf", 123L)
            call("wrapper_swap", r(e,"WR_X0"))
            s(e,"WR_X0", r(e,"SWAPPED"))
        }
        s(e,"MIX_FINISHED_X1", r(e,"WR_X0"))
        s(e,"MIX_FINISHED_X2", r(e,"WR_X1"))
    }
    private fun fn_response_prefix_math(args:LongArray) {
        val e = hashMapOf<String,Long>()
        e["w21"] = 0L
        e["w22"] = 0L
        e["w23"] = 0L
        e["w8"] = 0L
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["w13"] = 0L
        e["w24"] = 0L
        e["v"] = 0L
        e["rot26"] = 0L
        e["rot31"] = 0L
        e["rot10"] = 0L
        e["i"] = 0L
        e["passes"] = 0L
        e["1"] = args.getOrElse(0) { 0L }
        e["2"] = args.getOrElse(1) { 0L }
        e["3"] = args.getOrElse(2) { 0L }
        e["4"] = args.getOrElse(3) { 0L }
        e["5"] = args.getOrElse(4) { 0L }
        e["6"] = args.getOrElse(5) { 0L }
        e["7"] = args.getOrElse(6) { 0L }
        e["8"] = args.getOrElse(7) { 0L }
        e["9"] = args.getOrElse(8) { 0L }
        e["10"] = args.getOrElse(9) { 0L }
        e["11"] = args.getOrElse(10) { 0L }
        e["12"] = args.getOrElse(11) { 0L }
        e["13"] = args.getOrElse(12) { 0L }
        e["14"] = args.getOrElse(13) { 0L }
        s(e,"w21", r(e,"1"))
        s(e,"w22", r(e,"2"))
        s(e,"w23", r(e,"3"))
        s(e,"w8", r(e,"4"))
        e["w9"] = 0L
        e["w10"] = 0L
        e["w11"] = 0L
        e["w12"] = 0L
        e["w13"] = 0L
        e["w24"] = 0L
        e["v"] = 0L
        e["rot26"] = 0L
        e["rot31"] = 0L
        e["rot10"] = 0L
        e["i"] = 0L
        e["passes"] = 0L
        if ((r(e,"w21") and 0x01000000L) != 0L) {
            s(e,"w24", ((r(e,"w8") + r(e,"w22")) and 0xffffffffL))
            call("selected_wrapper", ((r(e,"w24") + 0x95b5ea08L) and 0xffffffffL), (r(e,"w24") or (r(e,"w22") shl 32L.toInt())), (r(e,"w21") or 0x2acda9c400000000L))
            s(e,"v", (r(e,"WR_X0") and 0xffffffffL))
            s(e,"w8", ((r(e,"w23") - r(e,"v")) and 0xffffffffL))
            call("response_table32", 0x69cL, r(e,"w8"))
            s(e,"w10", (r(e,"TABLE_RESULT") xor r(e,"w22")))
            call("response_table32", 0x34L, r(e,"w10"))
            s(e,"w9", ((r(e,"TABLE_RESULT") + r(e,"w24")) and 0xffffffffL))
            s(e,"w8", ((r(e,"w8") - r(e,"w9")) and 0xffffffffL))
            s(e,"w12", ((r(e,"w10") - r(e,"w8")) and 0xffffffffL))
            s(e,"w9", (r(e,"w9") xor r(e,"w12")))
            s(e,"w12", ((r(e,"w9") * 0xd9bff066L) and 0xffffffffL))
            s(e,"w10", ((r(e,"w12") + r(e,"w10")) and 0xffffffffL))
            s(e,"w8", ((r(e,"w12") + r(e,"w8")) and 0xffffffffL))
            s(e,"w9", (r(e,"w9") xor r(e,"w10")))
            call("response_ror32", r(e,"w10"), 26L)
            s(e,"rot26", r(e,"ROR_RESULT"))
            call("response_ror32", r(e,"w9"), 31L)
            s(e,"rot31", r(e,"ROR_RESULT"))
            s(e,"w23", (((r(e,"w8") + r(e,"rot26")) + r(e,"rot31")) and 0xffffffffL))
            s(e,"w22", ((r(e,"w10") - r(e,"w23")) and 0xffffffffL))
            call("response_ror32", r(e,"w22"), 26L)
            s(e,"w8", (((r(e,"ROR_RESULT") - 12228L) and 0xffffffffL) xor ((r(e,"w23") - 18792L) and 0xffffffffL)))
            s(e,"w24", ((r(e,"w9") - r(e,"w8")) and 0xffffffffL))
            call("selected_wrapper", ((r(e,"w24") + 0xddf91026L) and 0xffffffffL), (r(e,"w24") or (r(e,"w22") shl 32L.toInt())), (r(e,"w21") or 0x8fe98e5500000000UL.toLong()))
            s(e,"v", (r(e,"WR_X0") and 0xffffffffL))
            s(e,"w23", ((r(e,"w23") + r(e,"v")) and 0xffffffffL))
            call("response_ror32", r(e,"w23"), 29L)
            s(e,"w22", (((r(e,"ROR_RESULT") + 6864L) and 0xffffffffL) xor (((r(e,"w24") + 14906L) and 0xffffffffL) xor r(e,"w22"))))
            s(e,"w8", (r(e,"w24") xor r(e,"w21")))
        }
        if ((r(e,"w22") and (1L shl 19L.toInt())) != 0L) {
            s(e,"w8", (((r(e,"w21") * 0x5f92c7edL) + r(e,"w8")) and 0xffffffffL))
            call("response_table32", 0x130L, r(e,"w8"))
            s(e,"w9", ((r(e,"w23") - r(e,"TABLE_RESULT")) and 0xffffffffL))
            s(e,"w10", ((r(e,"w21") + 0x9d53be8eL) and 0xffffffffL))
            s(e,"w8", (r(e,"w8") xor r(e,"w10")))
            s(e,"w11", (((r(e,"w8") + r(e,"w10")) - r(e,"w9")) and 0xffffffffL))
            s(e,"w8", (r(e,"w8") xor r(e,"w22")))
            s(e,"w23", ((r(e,"w9") - r(e,"w11")) and 0xffffffffL))
            s(e,"w21", ((r(e,"w10") - r(e,"w11")) and 0xffffffffL))
        }
        s(e,"passes", (((r(e,"w21") shr 23L.toInt()) and 7L) + 1L))
        e["i"] = 0L
        s(e,"i", 0L)
        while ((if (r(e,"i") < r(e,"passes")) 1L else 0L) != 0L) {
            s(e,"w8", (r(e,"w8") xor r(e,"w22")))
            s(e,"w23", (((r(e,"w23") + r(e,"w22")) + r(e,"w8")) and 0xffffffffL))
            s(e,"w8", (r(e,"w8") xor r(e,"w21")))
            s(e,"w22", ((r(e,"w23") + r(e,"w22")) and 0xffffffffL))
            s(e,"i",r(e,"i") + 1L)
        }
        call("response_table32", 0x5bcL, r(e,"w22"))
        s(e,"w11", (r(e,"TABLE_RESULT") xor r(e,"w8")))
        s(e,"w10", ((r(e,"w11") + r(e,"w23")) and 0xffffffffL))
        s(e,"w9", ((r(e,"w10") + r(e,"w21")) and 0xffffffffL))
        s(e,"w8", (((r(e,"w9") + 0x6bad4b80L) and 0xffffffffL) xor r(e,"w22")))
        if ((r(e,"w8") and (1L shl 16L.toInt())) != 0L) {
            call("response_table32", 0x74cL, r(e,"w9"))
            s(e,"w11", ((r(e,"TABLE_RESULT") + r(e,"w11")) and 0xffffffffL))
            s(e,"w10", (((r(e,"w11") * 0xb1199e45L) + r(e,"w10")) and 0xffffffffL))
            call("response_ror32", r(e,"w10"), 16L)
            s(e,"w13", (((r(e,"ROR_RESULT") + 27176L) and 0xffffffffL) xor ((r(e,"w11") - 3965L) and 0xffffffffL)))
            s(e,"w9", ((r(e,"w9") - r(e,"w13")) and 0xffffffffL))
            s(e,"w11", (r(e,"w11") xor r(e,"w9")))
            call("response_ror32", r(e,"w11"), 21L)
            s(e,"w13", (((r(e,"ROR_RESULT") + 11464L) and 0xffffffffL) xor ((r(e,"w9") + 9576L) and 0xffffffffL)))
            s(e,"w10", ((r(e,"w13") + r(e,"w10")) and 0xffffffffL))
            call("response_ror32", r(e,"w10"), 26L)
            s(e,"rot10", r(e,"ROR_RESULT"))
            call("response_ror32", r(e,"w11"), 9L)
            s(e,"w9", (r(e,"w9") xor ((r(e,"rot10") + r(e,"ROR_RESULT")) and 0xffffffffL)))
            call("response_table32", 0x624L, r(e,"w9"))
            s(e,"w11", ((r(e,"w11") - r(e,"TABLE_RESULT")) and 0xffffffffL))
            s(e,"w10", (r(e,"w10") xor r(e,"w11")))
            s(e,"w9", (r(e,"w9") xor r(e,"w10")))
            s(e,"w10", ((r(e,"w10") + r(e,"w11")) and 0xffffffffL))
            s(e,"w12", ((r(e,"w11") - r(e,"w9")) and 0xffffffffL))
            s(e,"w9", (r(e,"w9") xor r(e,"w10")))
            s(e,"w11", (r(e,"w12") xor r(e,"w8")))
        }
        s(e,"RESPONSE_CALL_X1", ((r(e,"w9") and 0xffffffffL) or (r(e,"w8") shl 32L.toInt())))
        s(e,"RESPONSE_CALL_X2", ((r(e,"w10") and 0xffffffffL) or (r(e,"w11") shl 32L.toInt())))
    }
}
