package dev.brahmkshatriya.echo.extension

// Mechanically translated fixed-width Bash arithmetic leaves. No shell or Python runtime.
internal class PlayPlayLeaves {
    var leaf0 = 0L
    var leaf1 = 0L
    private val table = longArrayOf(
        4272183795L, 3391646368L, 1350169058L, 1384647726L, 2856865003L, 2083778362L, 3324063477L, 2585606290L, 2170746539L, 2837350491L, 2018032302L, 2427184720L,
        1982678190L, 2800575834L, 1886554651L, 1489702009L, 469222240L, 511286359L, 484141408L, 2199810777L, 1123200494L, 748819606L, 3569364616L, 4119315201L,
        334917228L, 2263370023L, 274343989L, 2020942661L, 629634939L, 1368993578L, 2660433005L, 1813679428L, 2836563846L, 1257188012L, 1060525822L, 4113610920L,
        1985530240L, 1932089351L, 2875100166L, 3374970807L, 742250612L, 2618224628L, 750617987L, 3448112630L, 274253598L, 110904232L, 2737853729L, 760435042L,
        2690973697L, 1224996106L, 3679394559L, 1823196591L, 1677132143L, 638240026L, 1237581476L, 1766301633L, 355035778L, 2651218773L, 3487057095L, 3317679701L,
        2308353444L, 678867040L, 2545041841L, 2547855441L, 2619376104L, 156098211L, 2962810315L, 3495413522L, 3482043355L, 1259123180L, 1851009192L, 501272101L,
        3846252891L, 2304742724L, 4043245886L, 905826727L, 674839913L, 1132387061L, 654515856L, 2030693192L, 1213480491L, 1290717032L, 1178700548L, 2728558042L,
        3154824062L, 1872205806L, 2585946837L, 1235738853L, 3900598995L, 3744496409L, 615244389L, 1603115534L, 1787290700L, 3915114686L, 3446931934L, 2978253148L,
        1517632220L, 422654174L, 1651312815L, 3342315698L, 730303874L, 308727494L, 1105493416L, 1557300471L, 891871269L, 3271201105L, 3422196324L, 669925381L,
        2990938203L, 1286003810L, 1650601198L, 371252647L, 203154497L, 2931420359L, 1944740330L, 3397010928L, 4246120579L, 579372236L, 3751430377L, 2484039762L,
        2856397155L, 3084555365L, 270470930L, 1247257498L, 130451802L, 2608752638L, 940689112L, 3749318443L, 837131381L, 2273883621L, 306829713L, 2165903758L,
        3916749249L, 1187677761L, 3120127163L, 2042576681L, 2451542228L, 763344516L, 3059945377L, 184602618L, 295183359L, 1398938919L, 1390252164L, 717554984L,
        773235768L, 1425263848L, 1162467030L, 3148252594L, 1478212186L, 1342166554L, 2157960407L, 4180530754L, 3665228572L, 705919077L, 1278304498L, 3254158651L,
        2171090494L, 1991281937L, 1783256080L, 2328189128L, 1179984071L, 3779238689L, 6396281L, 1084646871L, 186066048L, 1888359851L, 2775275483L, 3419185814L,
        651601729L, 1454303025L, 3835581131L, 1564007991L, 282813784L, 2067978079L, 1274674891L, 2837179054L, 242531200L, 268661924L, 2749953759L, 3360459006L,
        2428175855L, 3234621488L, 2075545478L, 2667517633L, 1921281261L, 3814565837L, 400891638L, 822133092L, 4142564395L, 4083756460L, 4287006826L, 504402996L,
        1648635676L, 2666269511L, 1301590186L, 2476619085L, 529666088L, 2383759587L, 1386274981L, 2117780582L, 4002800066L, 1739090205L, 3186971269L, 1077303153L,
        1685578892L, 3525798707L, 1610284265L, 1547494805L, 3610166125L, 3440362442L, 1007702154L, 438044435L, 4046493478L, 365851430L, 2338304741L, 2002179728L,
        2833134841L, 821181802L, 255392509L, 4152060628L, 511881405L, 2006883317L, 1679051180L, 1489968358L, 1631359595L, 3215511363L, 3154533224L, 3791498744L,
        1492400241L, 616333921L, 3993900155L, 1226737958L, 670949703L, 3893161125L, 403306134L, 1387511658L, 1589375868L, 3861481262L, 2716763713L, 607546250L,
        544739082L, 3899379142L, 1747113286L, 1117662784L, 3090415342L, 4002878212L, 3039874459L, 2034679438L, 2574808989L, 4026464108L, 3635424162L, 946432306L,
        564432726L, 111833319L, 3470316250L, 1755754995L, 4259366260L, 3297192082L, 969827126L, 1589222682L, 388679248L, 2574124217L, 2113905896L, 3194568657L,
        841810552L, 3093024418L, 533261615L, 2366941393L, 847323111L, 269407754L, 1194055027L, 1163896715L, 1216296111L, 3587088537L, 3190240828L, 1066601323L,
        543430724L, 3504879420L, 3339425537L, 477438755L, 3748390571L, 2314409535L, 3148101917L, 3771406686L, 1548179734L, 2729502565L, 2435406102L, 1023276944L,
        1643701895L, 3399010459L, 3116285058L, 1730042348L, 120190190L, 1389989354L, 2211777653L, 2680285568L, 3199200898L, 3600918146L, 662053525L, 2628622451L,
        2413029241L, 1224219928L, 300304518L, 4133499077L, 1823396558L, 3667163101L, 3067242679L, 3690053401L, 4237469009L, 2131136781L, 408102368L, 1956759819L,
        3309681995L, 3812275550L, 1379300724L, 4026097435L, 2179846865L, 1402442877L, 1339609638L, 942738971L, 1551691186L, 1222169372L, 1144266140L, 602615070L,
        74185766L, 1180416109L, 2767374507L, 3753711575L, 2932843346L, 4093705559L, 297506489L, 4104715824L, 1679497858L, 2866333547L, 1558015340L, 1950738471L,
        2349044480L, 1837623175L, 305247355L, 3724324118L, 110458322L, 3904618967L, 2447901806L, 2203898207L, 1568402540L, 2280924782L, 415081262L, 2044709463L,
        2533883971L, 3747627033L, 2345443803L, 348565807L, 4258404591L, 3444957573L, 1401143057L, 2031381486L, 2713220218L, 282857022L, 2410267378L, 4193387471L,
        727865477L, 1205627675L, 1832966877L, 2714362491L, 2035523148L, 2383525022L, 2267064653L, 71423360L, 1300644862L, 4023580338L, 3006974817L, 3926250875L,
        851600829L, 3129613995L, 2020707545L, 3444936045L, 21577155L, 1080059326L, 2303369964L, 2111366080L, 3735242975L, 3490652209L, 1555204899L, 1036698368L,
        2727356348L, 4010715522L, 3110862295L, 2864628253L, 1015312780L, 2824974153L, 2764868576L, 793970265L, 3362739784L, 72722495L, 2850799595L, 1231917998L,
        3023717430L, 839630769L, 1902189726L, 596344086L, 3932152900L, 1916535270L, 3939542605L, 2388498160L, 1765886849L, 2643294359L, 162627144L, 1615972471L,
        1441203212L, 582706294L, 1268627754L, 1363936401L, 3378178085L, 1491837070L, 609198718L, 1534460971L, 1087838981L, 2646236703L, 2596544533L, 2281144212L,
        1727568064L, 2737059333L, 1513239248L, 817664508L, 686704056L, 2190496331L, 310949988L, 1072377970L, 3113782979L, 428390875L, 1396289924L, 641898166L,
        3998434112L, 459246034L, 736001898L, 760460420L, 558664322L, 4227817830L, 3302196467L, 1727647520L, 648606240L, 1643135983L, 440481538L, 1237804508L,
        1631178668L, 179871177L, 3130752003L, 4078049032L, 1332510258L, 487619848L, 3405174144L, 1147934514L, 1107617765L, 2064608356L, 3384066792L, 1423396234L,
        2055314415L, 2269231733L, 1627813829L, 2990304591L, 1169200881L, 821802686L, 1652686033L, 3415218444L, 2074147531L, 2355984996L, 2255188095L, 1481581875L,
        3249113524L, 2228902656L, 1013424021L, 2619360301L, 1931566854L, 917146930L, 4044388305L, 1146439353L, 617273552L, 657576519L, 556291468L, 1668131885L,
        2130776568L, 1156912394L, 1593671844L, 4092639830L, 56975751L, 2921885201L, 3286818654L, 4168865193L, 2851381405L, 3729988175L, 1054559839L, 4162967250L,
        3862117362L, 862104180L, 1116450154L, 2296315745L, 3191246372L, 1646052623L, 1405489340L, 1915541043L, 1654986668L, 1815810443L, 3512754292L, 2788453830L,
        2782567720L, 792268394L, 2913231935L, 1125316113L, 2814295597L, 1980244004L, 3829999107L, 691968529L, 1489684002L, 1763827699L, 568753387L, 440467703L,
        3890565856L, 1875196806L, 4254232755L, 2901723008L, 799555900L, 3192964511L, 1824671704L, 2943012640L, 4025280493L, 2602072700L, 545581975L, 3117112094L,
        2095721629L, 3052347586L, 787119937L, 1161525050L, 2419518257L, 1466218615L, 2381461870L, 1663742671L, 568136012L, 553926451L, 280863578L, 2257978643L,
        590153101L, 3413051016L, 2787651853L, 2793581073L, 1321755478L, 419998416L, 1736095579L, 3675631551L, 2270193792L, 2626949200L, 3199958419L, 3841538791L,
        3691888535L, 4192625969L, 1620292524L, 3141451007L, 3403401963L, 4201996347L, 2680747254L, 2432366783L, 3583691547L, 4036409962L, 3032750765L, 2413114290L,
        3637308107L, 3374012218L, 3222490155L, 525562472L, 1809816102L, 1311535629L, 148026727L, 2432773527L, 645040960L, 3432860093L, 363359365L, 3817978253L,
        1334206012L, 3718757435L, 241393444L, 957000357L, 3327378123L, 4289305874L, 833198344L, 3748489519L, 622634010L, 3600328176L, 1470658531L, 4057683288L,
        2859917852L, 975718659L, 2096917813L, 2374488493L, 711496549L, 3141966115L, 2109782323L, 2069800772L, 2380211973L, 2088759952L, 4231932896L, 3899601384L,
        1671667282L, 498585517L, 2058884671L, 2571798897L, 386710668L, 2583670253L, 3032873396L, 162412664L, 3463592759L, 3928251238L, 1889471195L, 1755213061L,
        3829234049L, 2248855083L, 1612272820L, 3834239548L, 1337139727L, 1365620077L, 978077559L, 3263922313L, 3804073754L, 1425766987L, 992140893L, 2715835933L,
        4233890391L, 3232851983L, 1019190518L, 3665065564L, 251399709L, 29817166L, 61461060L, 721231596L, 486650073L, 2120156366L, 189305792L, 3869032982L,
        779872155L, 1874741980L, 1718151598L, 3864114783L, 1180611304L, 222785657L, 1709370791L, 217549637L, 512617056L, 3313945466L, 4198918448L, 1527370205L,
        556212059L, 2722041769L, 2216496203L, 649266620L, 3583793188L, 153475837L, 2215529130L, 3133229741L, 843738819L, 4257857585L, 1671277320L, 2410796048L,
        2455990117L, 483309267L, 2533306220L, 2828910864L, 3470027423L, 2978352515L, 1691787364L, 998041208L, 2416991137L, 816694776L, 1492260038L, 29630660L,
        710968671L, 2484973099L, 30296188L, 314784286L, 4172019546L, 1795483334L, 1946355200L, 4131462642L, 4273469806L, 1993393831L, 3406394984L, 1732762438L,
        3496450073L, 1535610014L, 292901090L, 969386998L, 2299313875L, 1451641202L, 593823777L, 356313823L, 3215799547L, 1842901235L, 1091611968L, 4204124123L,
        3501003735L, 1157102447L, 2369453447L, 2344871894L, 1384450199L, 1586912112L, 3888846544L, 729614022L, 3077298763L, 2095070637L, 4235026086L, 4247483036L,
        67850718L, 3895495861L, 786723011L, 112276680L, 4088032481L, 3035188172L, 2336202582L, 524671912L, 3867566625L, 2321310604L, 1730644525L, 3137041897L,
        2973236738L, 1857323751L, 2567835237L, 4234026230L, 98346068L, 3402937209L, 1867640374L, 4053737861L, 866024487L, 3084866949L, 2909102759L, 169073751L,
        1475814647L, 4115363832L, 3780025423L, 2573206055L, 2244732674L, 3480977918L, 4137544604L, 397964096L, 1331519861L, 3827387147L, 2431099041L, 3964213132L,
        740052668L, 1335019533L, 3353980529L, 2170120743L, 454104483L, 3919268517L, 588820242L, 1582188357L, 1298600300L, 496486348L, 1134995800L, 2525532845L,
        2590803183L, 199012097L, 453154463L, 1400271404L, 2789529667L, 2090026294L, 2713002182L, 1042597896L, 3212531996L, 3854710436L, 3806639790L, 1799920136L,
    )
    fun lookup(index:Int):Long = table[index]
    fun evaluate(slot:Int, x:Long, y:Long): Pair<Long,Long> {
        when(slot) {
            0 -> mix_cc0fc8(x,y)
            1 -> math_cc3304(x,y)
            3 -> math_cbf26c(x,y)
            4 -> mix_cbd8a8(x,y)
            5 -> math_cc20ec(x,y)
            6 -> math_cc2fb4(x,y)
            8 -> math_cbd5e8(x,y)
            9 -> math_cbee50(x,y)
            10 -> math_cc16f8(x,y)
            11 -> math_cbd384(x,y)
            12 -> math_cc209c(x,y)
            13 -> math_cbf59c(x,y)
            14 -> math_cbe664(x,y)
            15 -> math_cbdfd4(x,y)
            17 -> math_cbfce8(x,y)
            18 -> math_cbbee8(x,y)
            20 -> math_cbf7a8(x,y)
            21 -> math_cbf4c0(x,y)
            23 -> math_cbba78(x,y)
            24 -> math_cbe500(x,y)
            25 -> math_cbccd0(x,y)
            26 -> math_cbecc8(x,y)
            27 -> mix_cc0cc0(x,y)
            28 -> math_cbedf0(x,y)
            29 -> math_cbeff0(x,y)
            31 -> math_cc1478(x,y)
            32 -> math_cc21ac(x,y)
            33 -> math_cbbc28(x,y)
            37 -> math_cbc5b8(x,y)
            38 -> math_cbdbac(x,y)
            39 -> math_cbd550(x,y)
            40 -> math_cbe740(x,y)
            41 -> mix_cc1fa4(x,y)
            43 -> mix_cbd28c(x,y)
            44 -> math_cbcb28(x,y)
            45 -> math_cbd7a0(x,y)
            46 -> mix_cbff20(x,y)
            47 -> math_cc336c(x,y)
            48 -> math_cbdf98(x,y)
            49 -> math_cbd6d4(x,y)
            50 -> math_cbd860(x,y)
            52 -> math_cbe350(x,y)
            53 -> math_cc07e4(x,y)
            55 -> math_cbdd08(x,y)
            56 -> math_cc1b18(x,y)
            57 -> math_cbed80(x,y)
            60 -> mix_cbd25c(x,y)
            62 -> math_cbe14c(x,y)
            63 -> math_cc2470(x,y)
            64 -> math_cbe5b4(x,y)
            66 -> math_cc2a94(x,y)
            67 -> math_cbdcd4(x,y)
            68 -> math_cbc47c(x,y)
            69 -> mix_cbf548(x,y)
            70 -> math_cbd624(x,y)
            71 -> math_cbbfbc(x,y)
            72 -> math_cbd3dc(x,y)
            73 -> mix_cc09b4(x,y)
            74 -> math_cbf350(x,y)
            75 -> math_cbf21c(x,y)
            77 -> math_cc2ec4(x,y)
            79 -> math_cc04ac(x,y)
            80 -> math_cc02c0(x,y)
            81 -> mix_cbf8d0(x,y)
            82 -> math_cc13f8(x,y)
            83 -> math_cc3590(x,y)
            85 -> math_cbf828(x,y)
            86 -> mix_cc1c40(x,y)
            87 -> math_cbd144(x,y)
            89 -> math_cbf3d4(x,y)
            90 -> mix_cc3460(x,y)
            92 -> math_cbcabc(x,y)
            93 -> math_cc1ef8(x,y)
            94 -> math_cc2cb0(x,y)
            95 -> math_cc2020(x,y)
            96 -> math_cc2f30(x,y)
            98 -> math_cbc8ec(x,y)
            99 -> math_cbfe80(x,y)
            100 -> math_cc1e08(x,y)
            101 -> math_cc17b8(x,y)
            102 -> math_cc037c(x,y)
            103 -> math_cbe9e0(x,y)
            104 -> mix_cc2b40(x,y)
            105 -> math_cbde04(x,y)
            106 -> math_cc1bb0(x,y)
            107 -> math_cc1884(x,y)
            108 -> math_cc29d0(x,y)
            109 -> math_cbdab8(x,y)
            110 -> math_cbdc38(x,y)
            111 -> math_cc3064(x,y)
            114 -> math_cc2944(x,y)
            115 -> math_cc34d0(x,y)
            117 -> math_cbbdfc(x,y)
            118 -> mix_cbf990(x,y)
            119 -> math_cbd9fc(x,y)
            120 -> math_cbcfa4(x,y)
            123 -> math_cc0708(x,y)
            124 -> mix_cc2530(x,y)
            127 -> math_cbb9e0(x,y)
            128 -> mix_cc1160(x,y)
            129 -> mix_cbe4bc(x,y)
            132 -> math_cc11ac(x,y)
            134 -> math_cc2b80(x,y)
            137 -> math_cc2360(x,y)
            138 -> math_cbbaf0(x,y)
            139 -> math_cc12fc(x,y)
            140 -> mix_cbf6e8(x,y)
            145 -> math_cbcb64(x,y)
            147 -> math_cc31e8(x,y)
            148 -> mix_cc1080(x,y)
            152 -> math_cbd478(x,y)
            153 -> math_cc08f0(x,y)
            154 -> math_cbc218(x,y)
            156 -> math_cbf02c(x,y)
            158 -> math_cc03ec(x,y)
            162 -> math_cbd018(x,y)
            163 -> mix_cc06c0(x,y)
            164 -> math_cbfff4(x,y)
            165 -> math_cbd1fc(x,y)
            166 -> math_cbcba0(x,y)
            167 -> math_cc2290(x,y)
            168 -> mix_cc165c(x,y)
            170 -> math_cbc370(x,y)
            171 -> math_cbe988(x,y)
            173 -> math_cbbbc0(x,y)
            175 -> math_cbeb00(x,y)
            177 -> math_cc22e0(x,y)
            179 -> math_cbfb80(x,y)
            180 -> math_cc33dc(x,y)
            183 -> math_cc1a28(x,y)
            185 -> math_cbc810(x,y)
            187 -> math_cbced4(x,y)
            188 -> math_cc30fc(x,y)
            189 -> math_cbfd18(x,y)
            190 -> math_cbc7d4(x,y)
            191 -> math_cc2598(x,y)
            192 -> mix_cc0e54(x,y)
            193 -> math_cbddb0(x,y)
            195 -> math_cbebbc(x,y)
            197 -> mix_cc1290(x,y)
            198 -> math_cc2618(x,y)
            200 -> math_cbd744(x,y)
            201 -> math_cbf73c(x,y)
            202 -> math_cbc244(x,y)
            203 -> math_cbc700(x,y)
            204 -> math_cbd8e8(x,y)
            207 -> math_cbbd34(x,y)
            208 -> math_cbfa70(x,y)
            209 -> math_cbb870(x,y)
            211 -> mix_cbda60(x,y)
            212 -> math_cc1934(x,y)
            213 -> math_cbdf08(x,y)
            215 -> math_cc2db0(x,y)
            216 -> math_cbd434(x,y)
            218 -> math_cbf1e8(x,y)
            219 -> mix_cc0474(x,y)
            221 -> math_cbe07c(x,y)
            222 -> mix_cc13c4(x,y)
            223 -> math_cbd2dc(x,y)
            224 -> math_cc285c(x,y)
            227 -> math_cc0120(x,y)
            228 -> math_cbec38(x,y)
            230 -> math_cc0a70(x,y)
            231 -> mix_cc0d5c(x,y)
            232 -> math_cbfdc4(x,y)
            233 -> math_cbbd04(x,y)
            234 -> math_cbc670(x,y)
            236 -> math_cc0ae8(x,y)
            237 -> mix_cc0f34(x,y)
            238 -> math_cc2e10(x,y)
            239 -> mix_cc0df4(x,y)
            240 -> mix_cbefa8(x,y)
            241 -> math_cbb984(x,y)
            243 -> mix_cbc2e4(x,y)
            246 -> math_cbfa20(x,y)
            247 -> math_cbeab4(x,y)
            249 -> mix_cc1384(x,y)
            251 -> math_cbe218(x,y)
            253 -> math_cc0560(x,y)
            255 -> math_cc2c58(x,y)
            256 -> math_cbb8e4(x,y)
            257 -> math_cbcdc0(x,y)
            259 -> math_cc089c(x,y)
            260 -> mix_cbe3c4(x,y)
            261 -> math_cc0bac(x,y)
            262 -> mix_cc1010(x,y)
            264 -> math_cbef30(x,y)
            265 -> math_cbc748(x,y)
            268 -> math_cbe0fc(x,y)
            270 -> mix_cc1acc(x,y)
            271 -> math_cbd08c(x,y)
            272 -> math_cbfc90(x,y)
            273 -> mix_cbe484(x,y)
            274 -> mix_cc2d70(x,y)
            else -> error("Missing leaf slot $slot")
        }
        return leaf0 to leaf1
    }
    private fun math_cbb870(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x xor (y shr 32L.toInt()))
        b = (table[((a and 255L) + 337L).toInt()] + y)
        c = (x shr 32L.toInt())
        d = (y and 0xffffffffL)
        d = ((a - 22445L) xor (((d shr 8L.toInt()) or (d shl 24L.toInt())) - 10761L))
        e = ((b + 0x54033704L) xor (c - d))
        a = (((a - c) + d) and 0xffffffffL)
        b = ((b and 0xffffffffL) xor a)
        leaf0 = ((x and 0xffffffffL) or (e shl 32L.toInt()))
        leaf1 = (b or (((e + b) xor a) shl 32L.toInt()))
    }
    private fun math_cbb8e4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (y shr 32L.toInt())
        b = ((a + 0x9daaf57eL) xor y)
        c = (x shr 32L.toInt())
        d = (x - (((x shr 55L.toInt()) and 511L) or (c shl 9L.toInt())))
        e = (d and 0xffffffffL)
        f = (-((e shr 14L.toInt()) or (e shl 18L.toInt())))
        g = ((a + f) and 0xffffffffL)
        h = (b xor ((g shr 22L.toInt()) or (g shl 10L.toInt())))
        i = (h + a)
        d = ((((((y + i) - b) + c) + 0x7b3e8cd5L) xor ((((d - (a * 2L)) - (y * 2L)) - (c * 2L)) + b)) and 0xffffffffL)
        e = (((((y - e) + g) + c) + a) and 0xffffffffL)
        e = ((e shr 15L.toInt()) or (e shl 17L.toInt()))
        a = (((((y - b) + (h * 2L)) + c) + a) + e)
        b = (((((i + f) + 0x97b7a1ccL) xor (h + e)) and 0xffffffffL) or ((((((((d shr 19L.toInt()) or (d shl 13L.toInt())) - 27957L) xor (a + 0x97b74153L)) + i) + f) - 0x68485e34L) shl 32L.toInt()))
        leaf0 = (d or ((a + 0x97b7a1ccL) shl 32L.toInt()))
        leaf1 = b
    }
    private fun math_cbb984(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = table[((y and 255L) + 276L).toInt()]
        c = (x shr 32L.toInt())
        d = ((y shr 32L.toInt()) xor a)
        e = (((d * 0x7185b5c4L) xor y) and 0xffffffffL)
        f = ((b + c) xor e)
        b = ((d - b) - c)
        leaf0 = (a or (f shl 32L.toInt()))
        leaf1 = (((b and 0xffffffffL) xor e) or ((b - f) shl 32L.toInt()))
    }
    private fun math_cbb9e0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        a = (y shr 32L.toInt())
        b = (y * 417463390L)
        c = (x shr 32L.toInt())
        d = (((((a * 417463389L) + b) + c) + 0x63146ff5L) and 0xffffffffL)
        e = ((d shr 23L.toInt()) or (d shl 9L.toInt()))
        f = ((((x - (y * 2L)) - a) + 0x9ceb900bL) and 0xffffffffL)
        g = (((((-x) - c) + a) - 768755204L) and 0xffffffffL)
        h = ((((f shr 19L.toInt()) or (f shl 13L.toInt())) + ((g shr 16L.toInt()) or (g shl 16L.toInt()))) xor (a + y))
        d = ((h + d) and 0xffffffffL)
        i = ((d shr 4L.toInt()) or (d shl 28L.toInt()))
        j = (-((f shr 8L.toInt()) or (f shl 24L.toInt())))
        d = (((d + f) + e) xor (g - j))
        leaf0 = ((((f - i) + e) and 0xffffffffL) or (((((((((h * 2L) + (a * 417463388L)) + b) + (c * 2L)) + j) + x) + d) + 0x90e6b5f9L) shl 32L.toInt()))
        leaf1 = (((((h - d) - g) + j) and 0xffffffffL) or ((((d - f) + i) - e) shl 32L.toInt()))
    }
    private fun math_cbba78(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (y shr 32L.toInt())
        b = (table[((a and 255L) + 502L).toInt()] xor y)
        c = (x shr 32L.toInt())
        d = (c - y)
        e = (table[((b and 255L) + 288L).toInt()] xor d)
        c = (-(((x shr 44L.toInt()) and 0xfffffL) or (c shl 20L.toInt())))
        a = ((((d + a) + x) + c) and 0xffffffffL)
        c = (((x + c) - d) and 0xffffffffL)
        b = (b xor (((a shr 16L.toInt()) or (a shl 16L.toInt())) + ((c shr 31L.toInt()) or (c shl 1L.toInt()))))
        d = (((e + b) and 0xffffffffL) xor c)
        a = ((c + e) xor a)
        b = ((a + b) and 0xffffffffL)
        c = (b xor e)
        leaf0 = (d or (c shl 32L.toInt()))
        leaf1 = (b or (((a + d) + c) shl 32L.toInt()))
    }
    private fun math_cbbaf0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = ((y shr 32L.toInt()) and 0xffffffffL)
        c = (table[(((x - a) and 255L) + 401L).toInt()] + b)
        d = table[((c and 255L) + 418L).toInt()]
        e = (y and 0xffffffffL)
        f = ((((((e shr 22L.toInt()) or (e shl 10L.toInt())) - 7958L) xor (b - 27841L)) and 0xffffffffL) xor a)
        b = (b xor e)
        e = ((b shr 16L.toInt()) or (b shl 16L.toInt()))
        g = ((f shr 27L.toInt()) or (f shl 5L.toInt()))
        h = (((((((x - (f * 378959407L)) - (d * 378959408L)) + e) + g) - a) - b) and 0xffffffffL)
        a = ((((e + x) - a) + g) and 0xffffffffL)
        c = (c xor ((a shr 27L.toInt()) or (a shl 5L.toInt())))
        e = (((d + b) - c) and 0xffffffffL)
        a = (((a + f) + c) - b)
        c = (((f - ((((e shr 14L.toInt()) or (e shl 18L.toInt())) - 28086L) xor (a - 26723L))) + d) and 0xffffffffL)
        leaf0 = (h or (c shl 32L.toInt()))
        leaf1 = ((((((h * 2L) + d) + ((f + d) * 378959408L)) + b) and 0xffffffffL) or (((a - ((h shr 23L.toInt()) or (h shl 9L.toInt()))) - ((c shr 7L.toInt()) or (c shl 25L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbbbc0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = ((x shr 32L.toInt()) - y)
        d = (((y - b) + 0x731ad17cL) xor c)
        c = table[((c and 255L) + 201L).toInt()]
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = (((((c + ((c + b) * 0x576d3044L)) + y) + 0x731ad17cL) and 0xffffffffL) or (((c + b) + d) shl 32L.toInt()))
    }
    private fun math_cbbc28(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (((b * (-0x3e442f89L)) xor y) and 0xffffffffL)
        d = (((y + 0x402b951dL) xor (x shr 32L.toInt())) and 0xffffffffL)
        e = (c - table[(((b - d) and 255L) + 142L).toInt()])
        c = (c xor d)
        f = (table[((e and 255L) + 232L).toInt()] xor c)
        b = ((c + b) - d)
        c = ((e xor b) and 0xffffffffL)
        d = (f xor c)
        e = ((d shr 25L.toInt()) or (d shl 7L.toInt()))
        f = (-((((f shr 13L.toInt()) or (f shl 19L.toInt())) + 18932L) xor (c - 16634L)))
        g = (-table[(((b + f) and 255L) + 240L).toInt()])
        h = ((c + g) and 0xffffffffL)
        i = ((h shr 31L.toInt()) or (h shl 1L.toInt()))
        c = ((((((b + e) + f) + i) * 56905101L) + c) + g)
        d = ((h + d) and 0xffffffffL)
        g = ((c and 0xffffffffL) xor d)
        b = ((((b + f) + e) + i) xor ((d shr 26L.toInt()) or (d shl 6L.toInt())))
        leaf0 = (a or (g shl 32L.toInt()))
        leaf1 = (((c - table[((b and 255L) + 2L).toInt()]) and 0xffffffffL) or ((b xor ((g shr 10L.toInt()) or (g shl 22L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbbd04(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x shr 32L.toInt())
        b = ((x xor (((x shr 34L.toInt()) and 0x3fffffffL) or (a shl 30L.toInt()))) and 0xffffffffL)
        c = (y shr 32L.toInt())
        leaf0 = (b or ((a + y) shl 32L.toInt()))
        leaf1 = ((((c * 798456251L) xor y) and 0xffffffffL) or ((b + c) shl 32L.toInt()))
    }
    private fun math_cbbd34(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        var l = 0L
        a = (x shr 32L.toInt())
        b = (a * 0x571862ddL)
        c = (y * (-0x571862ddL))
        d = (y shr 32L.toInt())
        e = table[((a and 255L) + 505L).toInt()]
        f = (((d - e) - x) and 0xffffffffL)
        a = (a - y)
        e = ((e + x) xor (a - 535091863L))
        g = ((((f shr 13L.toInt()) or (f shl 19L.toInt())) + 24965L) xor (e - 31614L))
        f = (f xor e)
        h = (f * 0x571862ddL)
        a = (a - g)
        e = (a xor e)
        d = (d xor y)
        i = ((a + f) xor ((d - g) - f))
        j = (((((a * 0x571862ddL) + h) + e) xor (i - 0xc5d1060L)) and 0xffffffffL)
        k = table[((e and 255L) + 488L).toInt()]
        f = ((f + k) and 0xffffffffL)
        l = ((f shr 16L.toInt()) or (f shl 16L.toInt()))
        leaf0 = (j or (((((((((i - d) - (g * 0x571862dcL)) + h) - k) + b) + c) + e) - l) shl 32L.toInt()))
        leaf1 = ((((((((((d + (g * 0x571862dcL)) - h) + k) - b) - c) - e) + l) + j) and 0xffffffffL) or (((((f - (a * 0x571862ddL)) - h) - e) xor j) shl 32L.toInt()))
    }
    private fun math_cbbdfc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = ((x shr 32L.toInt()) - (y * 2L))
        c = ((y shr 32L.toInt()) xor a)
        d = (c * 0x6558cc86L)
        c = (table[((((y + b) - 52L) and 255L) + 264L).toInt()] xor c)
        e = (((d + y) - table[((c and 255L) + 383L).toInt()]) and 0xffffffffL)
        c = (c - table[(((((d * 127L) + b) - 97L) and 255L) + 322L).toInt()])
        b = ((b - d) - ((((e shr 7L.toInt()) or (e shl 25L.toInt())) - 2703L) xor (c + 28095L)))
        d = ((e - c) and 0xffffffffL)
        e = ((d shr 9L.toInt()) or (d shl 23L.toInt()))
        f = (b + e)
        b = ((b + 0x41b8759fL) and 0xffffffffL)
        b = (((((b shr 26L.toInt()) or (b shl 6L.toInt())) + e) xor c) and 0xffffffffL)
        c = ((((f + 0x41b8b5beL) and 0xffffffffL) xor d) xor ((((b shr 21L.toInt()) or (b shl 11L.toInt())) + 4311L) and 0xffffffffL))
        b = (b xor ((f + 0x41b8759fL) and 0xffffffffL))
        leaf0 = (a or (((f - c) + 0x41b8759fL) shl 32L.toInt()))
        leaf1 = ((c xor b) or ((((c - f) + b) + 0x4d1c629eL) shl 32L.toInt()))
    }
    private fun math_cbbee8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        var l = 0L
        a = (x shr 32L.toInt())
        b = (y shr 32L.toInt())
        c = (table[((((a - b) - y) and 255L) + 55L).toInt()] xor ((x - a) and 0xffffffffL))
        d = (table[((b and 255L) + 19L).toInt()] xor (y and 0xffffffffL))
        a = (((d + a) - b) - y)
        b = (c xor (a and 0xffffffffL))
        e = ((x - y) and 0xffffffffL)
        d = (d xor (((e shr 19L.toInt()) or (e shl 13L.toInt())) and 0xffffffffL))
        f = ((d shr 18L.toInt()) or (d shl 14L.toInt()))
        g = (f * 2L)
        c = (((c + 0x9f41087fL) and 0xffffffffL) xor e)
        e = ((c shr 15L.toInt()) or (c shl 17L.toInt()))
        h = (e * 2L)
        i = ((b shr 26L.toInt()) or (b shl 6L.toInt()))
        j = ((b shr 3L.toInt()) or (b shl 29L.toInt()))
        k = ((c shr 5L.toInt()) or (c shl 27L.toInt()))
        l = (((((((d + c) - k) - j) + i) * 46113590L) - 477444786L) xor (((((a - f) - e) - d) + j) + k))
        e = table[(((((((((-d) - a) + e) + f) + b) + k) + j) and 255L) + 393L).toInt()]
        leaf0 = (((((h - (a * 2L)) + g) + b) and 0xffffffffL) or (l shl 32L.toInt()))
        leaf1 = (((((((((c * 2L) + (i * 2L)) + d) - j) - k) + e) + 0x809059e5L) and 0xffffffffL) or ((((((((l - (a * 2L)) + e) + c) + h) + g) + b) + i) shl 32L.toInt()))
    }
    private fun math_cbbfbc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = (((y + 0x6ba2d8e6L) and 0xffffffffL) xor a)
        c = ((y shr 32L.toInt()) and 0xffffffffL)
        a = (table[((a and 255L) + 40L).toInt()] xor (x and 0xffffffffL))
        d = (((((a shr 12L.toInt()) or (a shl 20L.toInt())) + ((b shr 21L.toInt()) or (b shl 11L.toInt()))) and 0xffffffffL) xor c)
        e = ((c + y) and 0xffffffffL)
        f = (d xor e)
        g = (((b + e) + f) and 0xffffffffL)
        a = (((a - ((e shr 4L.toInt()) or (e shl 28L.toInt()))) - b) and 0xffffffffL)
        b = (((((b + c) + y) - f) * 8697063L) + (g xor a))
        c = (b and 0xffffffffL)
        a = (a xor e)
        e = (((g - (f * 2L)) xor ((a shr 13L.toInt()) or (a shl 19L.toInt()))) and 0xffffffffL)
        f = (c xor (((e shr 30L.toInt()) or (e shl 2L.toInt())) and 0xffffffffL))
        d = (g xor d)
        a = ((((((d shr 24L.toInt()) or (d shl 8L.toInt())) + 9896L) xor (b - 2401L)) and 0xffffffffL) xor a)
        b = (e xor ((a shr 21L.toInt()) or (a shl 11L.toInt())))
        c = (d - ((((c shr 2L.toInt()) or (c shl 30L.toInt())) - 3404L) xor (e + 25042L)))
        leaf0 = (f or (b shl 32L.toInt()))
        leaf1 = ((((c + f) and 0xffffffffL) xor a) or (((c - f) - b) shl 32L.toInt()))
    }
    private fun math_cbc218(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x and 0xffffffffL)
        b = (((x shr 32L.toInt()) - y) and 0xffffffffL)
        c = ((y shr 32L.toInt()) xor a)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((c + y) and 0xffffffffL) or ((c - ((b shr 18L.toInt()) or (b shl 14L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbc244(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x shr 32L.toInt())
        b = (x - a)
        c = ((((a + y) * 0x5e9343b1L) xor b) and 0xffffffffL)
        d = (y shr 32L.toInt())
        a = (((c - (d * 0x666c9530L)) - (y * 0x666c952fL)) + a)
        e = ((((x - (y * 0x71baef5eL)) + (b * 94959796L)) - (d * 0x6c11f6aaL)) and 0xffffffffL)
        e = ((e shr 19L.toInt()) or (e shl 13L.toInt()))
        f = ((c shr 30L.toInt()) or (c shl 2L.toInt()))
        g = (((b + d) - f) and 0xffffffffL)
        h = ((((g shr 6L.toInt()) or (g shl 26L.toInt())) - 9699L) xor (a + 13031L))
        leaf0 = (((a - e) and 0xffffffffL) or ((((((((x * 2L) + (y * 668498802L)) + (b * 0xb302f0dL)) + (d * 856205951L)) + h) + f) + c) shl 32L.toInt()))
        leaf1 = (((((((y - a) - h) - (g * 92747354L)) - (f * 92747355L)) + d) and 0xffffffffL) or (((g - (a * 0x4c061b58L)) + (e * 0x4c061b57L)) shl 32L.toInt()))
    }
    private fun math_cbc370(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (y and 0xffffffffL)
        c = ((b - 13367L) xor (((c shr 25L.toInt()) or (c shl 7L.toInt())) + 26566L))
        d = (x shr 32L.toInt())
        e = ((b - table[(((c + d) and 255L) + 29L).toInt()]) and 0xffffffffL)
        f = ((e shr 18L.toInt()) or (e shl 14L.toInt()))
        e = (((((f + (b * 2L)) + (y * 2L)) + c) + d) xor e)
        f = ((f + b) + y)
        g = (f and 0xffffffffL)
        b = ((((((g shr 21L.toInt()) or (g shl 11L.toInt())) + c) + d) + b) + y)
        c = ((f - e) and 0xffffffffL)
        d = (e + table[((b and 255L) + 433L).toInt()])
        e = (-((((c shr 5L.toInt()) or (c shl 27L.toInt())) + 2570L) xor (d + 28513L)))
        f = (((b + c) - d) + e)
        b = ((b + e) and 0xffffffffL)
        b = ((d - ((b shr 29L.toInt()) or (b shl 3L.toInt()))) and 0xffffffffL)
        c = ((c - ((((b shr 7L.toInt()) or (b shl 25L.toInt())) + 16498L) xor (f - 302789436L))) - d)
        d = table[((c and 255L) + 49L).toInt()]
        leaf0 = (a or (((f + d) - 302807892L) shl 32L.toInt()))
        leaf1 = (((c + table[((((b - f) + 84L) and 255L) + 22L).toInt()]) and 0xffffffffL) or ((((b - (f * 2L)) - d) + 605615784L) shl 32L.toInt()))
    }
    private fun math_cbc47c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (y shr 32L.toInt())
        b = (y and 0xffffffffL)
        c = ((x shr 32L.toInt()) and 0xffffffffL)
        b = ((((((y shr 62L.toInt()) and 3L) or (a shl 2L.toInt())) + ((b shr 20L.toInt()) or (b shl 12L.toInt()))) and 0xffffffffL) xor c)
        d = ((y - (((((y shr 61L.toInt()) and 7L) or (a shl 3L.toInt())) - 8847L) xor (((x - y) - c) - 17170L))) and 0xffffffffL)
        e = ((((((b shr 24L.toInt()) or (b shl 8L.toInt())) + x) - y) - c) + ((d shr 8L.toInt()) or (d shl 24L.toInt())))
        a = (((((y - x) + c) * 361911188L) + a) and 0xffffffffL)
        c = (table[((e and 255L) + 53L).toInt()] xor a)
        a = ((((e - 3021L) xor (((a shr 28L.toInt()) or (a shl 4L.toInt())) + 26465L)) + d) and 0xffffffffL)
        f = (table[((c and 255L) + 123L).toInt()] xor a)
        b = ((b - ((d shr 1L.toInt()) or (d shl 31L.toInt()))) and 0xffffffffL)
        d = (b xor ((a shr 18L.toInt()) or (a shl 14L.toInt())))
        a = (((((b shr 9L.toInt()) or (b shl 23L.toInt())) + 17034L) xor e) xor (a - 9656L))
        b = (table[((a and 255L) + 508L).toInt()] xor c)
        c = (((f + d) + b) and 0xffffffffL)
        e = (c xor (((d * 0x6edf652bL) + a) and 0xffffffffL))
        f = (f - b)
        g = (c xor table[((f and 255L) + 369L).toInt()])
        a = ((((a + c) + (d * 0x6edf652bL)) and 0xffffffffL) xor b)
        leaf0 = (e or (g shl 32L.toInt()))
        leaf1 = (((f xor ((a shr 13L.toInt()) or (a shl 19L.toInt()))) and 0xffffffffL) or ((a - ((((e shr 11L.toInt()) or (e shl 21L.toInt())) + 28341L) xor (g - 12867L))) shl 32L.toInt()))
    }
    private fun math_cbc5b8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x xor (y shr 32L.toInt()))
        b = (y - table[((a and 255L) + 28L).toInt()])
        c = (((y * 0x7d36d5daL) xor (x shr 32L.toInt())) and 0xffffffffL)
        a = ((((-c) - b) + a) and 0xffffffffL)
        d = (b xor ((a shr 19L.toInt()) or (a shl 13L.toInt())))
        b = (table[((b and 255L) + 53L).toInt()] xor c)
        c = ((d + 0x5ce390adL) xor b)
        a = (a xor b)
        b = ((a - c) and 0xffffffffL)
        e = ((b shr 3L.toInt()) or (b shl 29L.toInt()))
        a = ((((c - 5765L) xor (((a shr 3L.toInt()) or (a shl 29L.toInt())) + 3744L)) + d) and 0xffffffffL)
        d = ((a shr 13L.toInt()) or (a shl 19L.toInt()))
        leaf0 = ((x and 0xffffffffL) or (((c - e) - d) shl 32L.toInt()))
        leaf1 = ((a xor (((b shr 9L.toInt()) or (b shl 23L.toInt())) and 0xffffffffL)) or (((((c - e) - d) * 60078933L) xor b) shl 32L.toInt()))
    }
    private fun math_cbc670(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (y - b)
        d = (y and 0xffffffffL)
        d = (((x shr 32L.toInt()) - ((d shr 30L.toInt()) or (d shl 2L.toInt()))) and 0xffffffffL)
        b = ((((d shr 23L.toInt()) or (d shl 9L.toInt())) + 6264L) xor ((c - 1341L) xor b))
        e = (-table[((b and 255L) + 138L).toInt()])
        d = (((c * 341757537L) and 0xffffffffL) xor d)
        f = (((c + e) and 0xffffffffL) xor d)
        b = (b xor d)
        leaf0 = (a or (f shl 32L.toInt()))
        leaf1 = ((((c + e) - (b * 830537040L)) and 0xffffffffL) or ((((f shr 16L.toInt()) or (f shl 16L.toInt())) + b) shl 32L.toInt()))
    }
    private fun math_cbc700(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (y and 0xffffffffL)
        b = (x shr 32L.toInt())
        a = ((x - ((a shr 23L.toInt()) or (a shl 9L.toInt()))) - (((x shr 40L.toInt()) and 0xffffffL) or (b shl 24L.toInt())))
        c = (y shr 32L.toInt())
        leaf0 = ((a and 0xffffffffL) or ((b - table[((y and 255L) + 226L).toInt()]) shl 32L.toInt()))
        leaf1 = (((c + y) and 0xffffffffL) or ((a + c) shl 32L.toInt()))
    }
    private fun math_cbc748(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (y xor ((b shr 23L.toInt()) or (b shl 9L.toInt())))
        d = (-c)
        e = (x shr 32L.toInt())
        f = (e * (-2L))
        g = (y * 2L)
        e = ((((((d - b) + f) + g) + 0x9aec5c59L) xor ((c + e) - y)) and 0xffffffffL)
        b = ((((d - ((((e shr 1L.toInt()) or (e shl 31L.toInt())) - 4150L) xor (c - 21937L))) - b) + f) + g)
        c = (((-(b * 2L)) - d) and 0xffffffffL)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((((b - ((((c shr 20L.toInt()) or (c shl 12L.toInt())) + 2531L) xor (b + 15459L))) - e) - d) and 0xffffffffL) or (((-(b * 3L)) - d) shl 32L.toInt()))
    }
    private fun math_cbc7d4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x shr 32L.toInt())
        b = (a xor y)
        c = (y shr 32L.toInt())
        a = (((((x - (((x shr 55L.toInt()) and 511L) or (a shl 9L.toInt()))) + b) + c) + y) and 0xffffffffL)
        leaf0 = (a or (((b - c) - y) shl 32L.toInt()))
        leaf1 = (((a - c) and 0xffffffffL) or ((((y - a) + (c * 2L)) xor a) shl 32L.toInt()))
    }
    private fun math_cbc810(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = ((x - table[((a and 255L) + 21L).toInt()]) and 0xffffffffL)
        c = (y shr 32L.toInt())
        a = (((c + y) and 0xffffffffL) xor a)
        d = (b xor ((a shr 17L.toInt()) or (a shl 15L.toInt())))
        e = ((b shr 3L.toInt()) or (b shl 29L.toInt()))
        b = (-((b - 14836L) xor ((((y shr 52L.toInt()) and 4095L) or (c shl 12L.toInt())) - 15212L)))
        f = (((y + b) + (((-e) - c) * 710334018L)) and 0xffffffffL)
        c = (((e + c) - d) and 0xffffffffL)
        e = (f xor c)
        g = (-((c shr 2L.toInt()) or (c shl 30L.toInt())))
        h = (-((f shr 28L.toInt()) or (f shl 4L.toInt())))
        i = (((d + e) + g) + h)
        a = ((y + b) xor a)
        b = table[(((d - a) and 255L) + 104L).toInt()]
        c = (c xor b)
        a = (((a + g) + h) xor (e + c))
        d = (i + a)
        b = (f xor b)
        e = table[((i and 255L) + 430L).toInt()]
        leaf0 = ((d and 0xffffffffL) or ((a xor ((b shr 20L.toInt()) or (b shl 12L.toInt()))) shl 32L.toInt()))
        leaf1 = (((((e - c) * 0x4dbd6ff9L) and 0xffffffffL) xor b) or (((d + c) - e) shl 32L.toInt()))
    }
    private fun math_cbc8ec(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x shr 32L.toInt())
        b = (x - a)
        c = (y and 0xffffffffL)
        a = (a - ((c shr 19L.toInt()) or (c shl 13L.toInt())))
        d = (y shr 32L.toInt())
        leaf0 = ((b and 0xffffffffL) or (a shl 32L.toInt()))
        leaf1 = ((((d + 0x7d9e587L) and 0xffffffffL) xor c) or ((((a + 15319L) xor d) xor (b + 2882L)) shl 32L.toInt()))
    }
    private fun math_cbcabc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (x shr 32L.toInt())
        d = ((table[(((b + y) and 255L) + 473L).toInt()] + (y * 433106463L)) + c)
        c = (((b + (y * 433106464L)) + c) xor b)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = ((((table[((c and 255L) + 136L).toInt()] + b) + y) and 0xffffffffL) or ((d + c) shl 32L.toInt()))
    }
    private fun math_cbcb28(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = ((y xor (x shr 32L.toInt())) and 0xffffffffL)
        c = ((y shr 32L.toInt()) xor a)
        d = (c + y)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = ((d and 0xffffffffL) or ((((((b shr 15L.toInt()) or (b shl 17L.toInt())) + 13139L) xor (d + 8119L)) + c) shl 32L.toInt()))
    }
    private fun math_cbcb64(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x shr 32L.toInt())
        b = ((a + x) and 0xffffffffL)
        a = (a - y)
        c = (y shr 32L.toInt())
        leaf0 = (b or (a shl 32L.toInt()))
        leaf1 = (((y - (((y shr 35L.toInt()) and 536870911L) or (c shl 29L.toInt()))) and 0xffffffffL) or ((((((b shr 23L.toInt()) or (b shl 9L.toInt())) + 14721L) xor (a - 651L)) + c) shl 32L.toInt()))
    }
    private fun math_cbcba0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = y
        c = (a xor b)
        a = (((x - y) - a) and 0xffffffffL)
        d = (table[((c and 255L) + 11L).toInt()] xor a)
        e = (y shr 32L.toInt())
        f = (a + e)
        a = ((((a shr 3L.toInt()) or (a shl 29L.toInt())) + (((y shr 50L.toInt()) and 16383L) or (e shl 14L.toInt()))) xor b)
        b = (a xor (f + 44579393L))
        e = (((((a - d) + f) - c) - 893238740L) xor b)
        a = (((c - a) + b) - e)
        c = ((((d + e) + 716936927L) xor (a + 0x62a18ef4L)) and 0xffffffffL)
        g = (e xor table[(((((f - (d * 2L)) + b) + 65L) and 255L) + 211L).toInt()])
        h = table[((g and 255L) + 277L).toInt()]
        i = ((((a + c) + h) + 0x62a18ef4L) and 0xffffffffL)
        a = (a + h)
        b = (((((f - (d * 3L)) + b) - e) + 420088476L) and 0xffffffffL)
        d = (g - ((b shr 8L.toInt()) or (b shl 24L.toInt())))
        e = (((a - table[((d and 255L) + 504L).toInt()]) + 0x62a18ef4L) and 0xffffffffL)
        a = (-((((c shr 6L.toInt()) or (c shl 26L.toInt())) - 27490L) xor (a + 0x62a1c926L)))
        leaf0 = (i or (e shl 32L.toInt()))
        leaf1 = (((((b + d) + a) + 35429099L) and 0xffffffffL) or ((((((i shr 10L.toInt()) or (i shl 22L.toInt())) + ((e shr 12L.toInt()) or (e shl 20L.toInt()))) + b) + a) shl 32L.toInt()))
    }
    private fun math_cbccd0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (y shr 32L.toInt())
        b = (y xor (((y shr 46L.toInt()) and 262143L) or (a shl 18L.toInt())))
        c = ((x shr 32L.toInt()) and 0xffffffffL)
        d = (((b + a) - c) - x)
        e = (((y * 0x403dd1b5L) and 0xffffffffL) xor c)
        f = ((((e shr 25L.toInt()) or (e shl 7L.toInt())) + 32632L) xor (b - 32766L))
        c = (((a - (c * 2L)) - (x * 2L)) - f)
        g = (-table[((c and 255L) + 386L).toInt()])
        h = (d + g)
        b = (((b + e) xor d) and 0xffffffffL)
        d = ((h and 0xffffffffL) xor b)
        a = ((((f + b) - e) + g) + a)
        b = (((a - h) xor c) and 0xffffffffL)
        c = (h - ((a + 22053L) xor (((b shr 1L.toInt()) or (b shl 31L.toInt())) + 18957L)))
        e = ((((((d shr 4L.toInt()) or (d shl 28L.toInt())) - 15364L) xor a) xor (c - 12962L)) and 0xffffffffL)
        f = (c and 0xffffffffL)
        a = ((-(a * 0xa365e71L)) xor b)
        b = ((((f shr 21L.toInt()) or (f shl 11L.toInt())) - 8747L) xor (a - 17002L))
        leaf0 = (e or ((b + d) shl 32L.toInt()))
        leaf1 = (((c - a) and 0xffffffffL) or ((((a - e) - b) - d) shl 32L.toInt()))
    }
    private fun math_cbcdc0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        var l = 0L
        a = ((y shr 32L.toInt()) xor x)
        b = (((a * 0x5726da80L) + (y * 960238320L)) xor (x shr 32L.toInt()))
        c = (table[((b and 255L) + 439L).toInt()] + x)
        d = (c and 0xffffffffL)
        e = ((d shr 31L.toInt()) or (d shl 1L.toInt()))
        f = (e + a)
        g = (f and 0xffffffffL)
        h = ((g shr 31L.toInt()) or (g shl 1L.toInt()))
        c = (c - (h * 2L))
        i = (y * (-2L))
        j = table[(((((c + (a * 80L)) + i) - b) and 255L) + 352L).toInt()]
        k = (j * (-0x78835e58L))
        e = (((((y + k) + (g * 0x61a49480L)) + h) + (e * 634916136L)) and 0xffffffffL)
        l = (-((e shr 26L.toInt()) or (e shl 6L.toInt())))
        h = table[((((((h - (a * 40L)) + y) + b) + l) and 255L) + 21L).toInt()]
        a = ((((c + (a * 0x4bb01a50L)) + i) - b) + h)
        b = (a xor (f + j))
        c = (((((((k - (g * 0x78835e58L)) + h) + d) + b) + l) - 0x5d6b4a31L) and 0xffffffffL)
        d = (a xor ((c shr 28L.toInt()) or (c shl 4L.toInt())))
        f = ((b - d) and 0xffffffffL)
        a = ((((a + e) + b) - ((f shr 23L.toInt()) or (f shl 9L.toInt()))) and 0xffffffffL)
        b = (((((a shr 9L.toInt()) or (a shl 23L.toInt())) + ((f shr 12L.toInt()) or (f shl 20L.toInt()))) and 0xffffffffL) xor c)
        c = ((d - ((a shr 16L.toInt()) or (a shl 16L.toInt()))) - ((b shr 2L.toInt()) or (b shl 30L.toInt())))
        d = ((c * 869855497L) xor f)
        a = (((a + c) + d) and 0xffffffffL)
        b = (a xor b)
        leaf0 = (((c - b) and 0xffffffffL) or (b shl 32L.toInt()))
        leaf1 = (a or (d shl 32L.toInt()))
    }
    private fun math_cbced4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (y shr 32L.toInt())
        b = (a xor y)
        c = (x shr 32L.toInt())
        d = (c + y)
        c = (d + (c xor x))
        a = (c + a)
        e = (b - table[((a and 255L) + 426L).toInt()])
        f = table[((e and 255L) + 309L).toInt()]
        g = (c and 0xffffffffL)
        a = (((((d - b) + 30541L) xor a) xor (((g shr 16L.toInt()) or (g shl 16L.toInt())) - 20031L)) and 0xffffffffL)
        e = (e - ((a shr 22L.toInt()) or (a shl 10L.toInt())))
        g = ((((f - b) + d) and 0xffffffffL) xor table[((e and 255L) + 310L).toInt()])
        a = (a xor (((c - b) + d) and 0xffffffffL))
        b = ((a shr 4L.toInt()) or (a shl 28L.toInt()))
        c = ((c - f) and 0xffffffffL)
        d = ((c shr 2L.toInt()) or (c shl 30L.toInt()))
        f = ((g shr 27L.toInt()) or (g shl 5L.toInt()))
        leaf0 = (((c + g) and 0xffffffffL) or (((e + g) + b) shl 32L.toInt()))
        leaf1 = ((((((e + b) - a) + d) + f) and 0xffffffffL) or ((((a - d) - f) xor (((-c) - g) * 0x6a2c336bL)) shl 32L.toInt()))
    }
    private fun math_cbcfa4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = ((y shr 32L.toInt()) and 0xffffffffL)
        b = (a + y)
        c = ((x shr 32L.toInt()) and 0xffffffffL)
        d = (((((x shr 62L.toInt()) and 3L) or (c shl 2L.toInt())) + x) and 0xffffffffL)
        c = (((y * 603504426L) and 0xffffffffL) xor c)
        e = ((((b - 1549L) and 0xffffffffL) xor d) xor ((((c shr 13L.toInt()) or (c shl 19L.toInt())) - 4722L) and 0xffffffffL))
        a = (d xor a)
        leaf0 = (e or ((c + b) shl 32L.toInt()))
        leaf1 = ((a xor (b and 0xffffffffL)) or ((table[((e and 255L) + 325L).toInt()] + a) shl 32L.toInt()))
    }
    private fun math_cbd018(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x shr 32L.toInt())
        b = (a - table[((y and 255L) + 102L).toInt()])
        a = (((a * (-924335779L)) xor x) and 0xffffffffL)
        c = ((b and 0xffffffffL) xor a)
        d = (y shr 32L.toInt())
        e = table[((d and 255L) + 75L).toInt()]
        a = ((a + 0xa00f0eL) xor d)
        leaf0 = (c or (((b - y) + e) shl 32L.toInt()))
        leaf1 = (((((y - e) - a) + 993413803L) and 0xffffffffL) or ((c + a) shl 32L.toInt()))
    }
    private fun math_cbd08c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = ((((((x shr 42L.toInt()) and 4194303L) or (a shl 22L.toInt())) + x) xor (y shr 32L.toInt())) and 0xffffffffL)
        c = ((y xor ((b shr 14L.toInt()) or (b shl 18L.toInt()))) and 0xffffffffL)
        a = (a xor ((c shr 9L.toInt()) or (c shl 23L.toInt())))
        d = (a xor x)
        b = (b - table[((d and 255L) + 178L).toInt()])
        e = ((((b + c) - a) + d) xor b)
        f = (e xor (b + c))
        d = ((e + ((((b + c) - a) - f) * 0x4b26b12fL)) - d)
        g = (d and 0xffffffffL)
        a = (((((-((g shr 23L.toInt()) or (g shl 9L.toInt()))) - b) - c) + a) + f)
        b = ((((e - g) + f) and 0xffffffffL) xor table[((a and 255L) + 197L).toInt()])
        c = (((g - ((b shr 19L.toInt()) or (b shl 13L.toInt()))) - f) and 0xffffffffL)
        d = ((d - ((c shr 28L.toInt()) or (c shl 4L.toInt()))) and 0xffffffffL)
        e = ((d shr 2L.toInt()) or (d shl 30L.toInt()))
        leaf0 = (((((a - e) * 814336005L) + b) and 0xffffffffL) or ((a - e) shl 32L.toInt()))
        leaf1 = (d or (c shl 32L.toInt()))
    }
    private fun math_cbd144(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = ((((y - 18032L) xor x) xor ((((x shr 61L.toInt()) and 7L) or (a shl 3L.toInt())) - 9443L)) and 0xffffffffL)
        c = (b xor (((y - a) * 0x565701f6L) + 0x7622bb7aL))
        d = (y shr 32L.toInt())
        e = (y - d)
        f = (e and 0xffffffffL)
        f = ((f shr 5L.toInt()) or (f shl 27L.toInt()))
        b = (d - ((b shr 22L.toInt()) or (b shl 10L.toInt())))
        d = (b and 0xffffffffL)
        d = ((d shr 21L.toInt()) or (d shl 11L.toInt()))
        g = ((((c - a) + y) + f) + d)
        e = ((e - table[((b and 255L) + 474L).toInt()]) and 0xffffffffL)
        b = (((b - c) + 0xd3f61154L) and 0xffffffffL)
        leaf0 = (((g - 916484441L) and 0xffffffffL) or ((e xor ((((a - y) - f) - d) + 916484441L)) shl 32L.toInt()))
        leaf1 = ((e xor b) or ((table[(((g - 89L) and 255L) + 190L).toInt()] xor b) shl 32L.toInt()))
    }
    private fun math_cbd1fc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x shr 32L.toInt())
        b = ((((x + y) - (a * 2L)) - 808657780L) and 0xffffffffL)
        c = (y shr 32L.toInt())
        d = ((c + y) and 0xffffffffL)
        e = (((a - y) + 0xdaaa3481L) and 0xffffffffL)
        a = (((x - a) + 0xaa77110dL) and 0xffffffffL)
        a = ((((e shr 9L.toInt()) or (e shl 23L.toInt())) + ((a shr 22L.toInt()) or (a shl 10L.toInt()))) xor c)
        leaf0 = (b or ((((d shr 11L.toInt()) or (d shl 21L.toInt())) + e) shl 32L.toInt()))
        leaf1 = (((a + d) and 0xffffffffL) or ((a + ((b shr 23L.toInt()) or (b shl 9L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbd2dc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = (b xor ((b - y) - ((y shr 32L.toInt()) xor a)))
        d = ((c + b) - y)
        b = ((((b * 2L) - y) - 428390508L) and 0xffffffffL)
        c = ((((b shr 26L.toInt()) or (b shl 6L.toInt())) + c) and 0xffffffffL)
        b = ((d and 0xffffffffL) xor b)
        e = (-((((c shr 30L.toInt()) or (c shl 2L.toInt())) + 8830L) xor (b - 28343L)))
        f = (((d + e) and 0xffffffffL) xor b)
        b = (c xor b)
        c = (((((((f shr 19L.toInt()) or (f shl 13L.toInt())) + d) + e) - b) + 0x95d43006L) xor b)
        b = ((((((-d) - e) + f) + b) + 0x6a2bcffaL) and 0xffffffffL)
        d = ((b shr 9L.toInt()) or (b shl 23L.toInt()))
        leaf0 = (a or ((c + d) shl 32L.toInt()))
        leaf1 = ((((b + c) + f) and 0xffffffffL) or ((((c * 2L) + f) + d) shl 32L.toInt()))
    }
    private fun math_cbd384(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (y shr 32L.toInt())
        b = (table[((a and 255L) + 200L).toInt()] xor (y and 0xffffffffL))
        c = ((x shr 32L.toInt()) and 0xffffffffL)
        d = (((y * 67953449L) and 0xffffffffL) xor c)
        e = ((b + d) and 0xffffffffL)
        c = (((c + y) xor x) and 0xffffffffL)
        f = (e xor c)
        a = ((a - ((c shr 31L.toInt()) or (c shl 1L.toInt()))) - ((d shr 17L.toInt()) or (d shl 15L.toInt())))
        leaf0 = (f or (e shl 32L.toInt()))
        leaf1 = ((b xor (a and 0xffffffffL)) or ((a - f) shl 32L.toInt()))
    }
    private fun math_cbd3dc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = ((x shr 32L.toInt()) - y)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = (((b + c) xor y) and 0xffffffffL)
        b = (b and 0xffffffffL)
        e = (d xor b)
        b = (c xor (((b shr 21L.toInt()) or (b shl 11L.toInt())) and 0xffffffffL))
        c = (d xor b)
        d = ((e - ((c shr 4L.toInt()) or (c shl 28L.toInt()))) and 0xffffffffL)
        b = ((b - e) and 0xffffffffL)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = ((((((b shr 8L.toInt()) or (b shl 24L.toInt())) + c) + ((d shr 20L.toInt()) or (d shl 12L.toInt()))) and 0xffffffffL) or ((((d shr 16L.toInt()) or (d shl 16L.toInt())) + b) shl 32L.toInt()))
    }
    private fun math_cbd434(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (y shr 32L.toInt())
        b = (y and 0xffffffffL)
        c = (x shr 32L.toInt())
        b = (((((y shr 61L.toInt()) and 7L) or (a shl 3L.toInt())) + ((b shr 22L.toInt()) or (b shl 10L.toInt()))) xor c)
        c = (c + x)
        d = ((b xor c) and 0xffffffffL)
        e = (y - (((y shr 35L.toInt()) and 536870911L) or (a shl 29L.toInt())))
        leaf0 = (d or ((b xor e) shl 32L.toInt()))
        leaf1 = ((((e + c) + a) and 0xffffffffL) or (((c + a) - d) shl 32L.toInt()))
    }
    private fun math_cbd478(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (table[((y and 255L) + 497L).toInt()] xor (x shr 32L.toInt()))
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = (c xor y)
        c = (((b * (-322597551L)) and 0xffffffffL) xor c)
        b = ((b + d) + c)
        d = ((((b - 2785L) xor (((c shr 23L.toInt()) or (c shl 9L.toInt())) - 8138L)) + d) and 0xffffffffL)
        c = ((b and 0xffffffffL) xor c)
        e = (d xor c)
        b = (((b - ((c shr 6L.toInt()) or (c shl 26L.toInt()))) - ((d shr 22L.toInt()) or (d shl 10L.toInt()))) and 0xffffffffL)
        c = (((b shr 14L.toInt()) or (b shl 18L.toInt())) + c)
        d = ((((b + c) + e) - 0x5c10ced3L) and 0xffffffffL)
        c = ((e - table[((c and 255L) + 70L).toInt()]) and 0xffffffffL)
        b = (((((d shr 19L.toInt()) or (d shl 13L.toInt())) + ((c shr 21L.toInt()) or (c shl 11L.toInt()))) + b) + e)
        c = (c xor (((d shr 20L.toInt()) or (d shl 12L.toInt())) and 0xffffffffL))
        d = (((b + 0xa3ef312dL) and 0xffffffffL) xor d)
        leaf0 = (a or (((b + c) + 0xa3ef312dL) shl 32L.toInt()))
        leaf1 = ((d xor c) or ((((b + d) + c) + 0xa3ef312dL) shl 32L.toInt()))
    }
    private fun math_cbd550(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x shr 32L.toInt())
        b = ((y * 0x6944f4e5L) xor a)
        c = (y shr 32L.toInt())
        d = ((b + y) - c)
        a = table[((a and 255L) + 435L).toInt()]
        b = ((((y - c) - x) + a) xor b)
        e = (d - b)
        f = (x * (-2L))
        g = (a * 2L)
        b = (b xor ((y + f) + g))
        h = (e + b)
        d = (d and 0xffffffffL)
        a = ((((c - x) + a) xor ((d shr 9L.toInt()) or (d shl 23L.toInt()))) and 0xffffffffL)
        c = (((((a shr 4L.toInt()) or (a shl 28L.toInt())) + y) + f) + g)
        a = (a xor e)
        leaf0 = ((h and 0xffffffffL) or (((c + a) xor b) shl 32L.toInt()))
        leaf1 = (((c - a) and 0xffffffffL) or ((a xor (h + 0xbeec91fbL)) shl 32L.toInt()))
    }
    private fun math_cbd5e8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (y and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = ((x - ((a shr 11L.toInt()) or (a shl 21L.toInt()))) - (((x shr 59L.toInt()) and 31L) or (b shl 5L.toInt())))
        d = (y shr 32L.toInt())
        leaf0 = ((c and 0xffffffffL) or (((b - ((a shr 25L.toInt()) or (a shl 7L.toInt()))) - d) shl 32L.toInt()))
        leaf1 = (((y - (d * 0x6147ab45L)) and 0xffffffffL) or ((c + d) shl 32L.toInt()))
    }
    private fun math_cbd624(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = (table[((a and 255L) + 376L).toInt()] xor x)
        c = (y and 0xffffffffL)
        a = (a xor (((c shr 8L.toInt()) or (c shl 24L.toInt())) and 0xffffffffL))
        c = (y shr 32L.toInt())
        d = (c + y)
        e = (d and 0xffffffffL)
        f = (b xor (((a shr 10L.toInt()) or (a shl 22L.toInt())) + ((e shr 27L.toInt()) or (e shl 5L.toInt()))))
        a = (a xor e)
        e = ((f + a) and 0xffffffffL)
        b = (b xor c)
        c = (d - b)
        a = (table[((c and 255L) + 75L).toInt()] + a)
        d = (e xor (a and 0xffffffffL))
        b = ((f * 0x5aea52e5L) xor b)
        c = (b xor c)
        e = ((e shr 26L.toInt()) or (e shl 6L.toInt()))
        leaf0 = (d or ((a - table[((c and 255L) + 25L).toInt()]) shl 32L.toInt()))
        leaf1 = (((((c - b) + e) - d) and 0xffffffffL) or (((b - e) - table[((d and 255L) + 309L).toInt()]) shl 32L.toInt()))
    }
    private fun math_cbd6d4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x shr 32L.toInt())
        b = (x xor (((x shr 61L.toInt()) and 7L) or (a shl 3L.toInt())))
        c = (y shr 32L.toInt())
        d = ((b + c) xor (y - c))
        e = (((table[((((((y * 11L) - (a * 12L)) + (b * 2L)) + (c * 2L)) and 255L) + 117L).toInt()] + d) and 0xffffffffL) or ((((((a * 2L) - (y * 4L)) + b) + (c * 3L)) + d) shl 32L.toInt()))
        leaf0 = ((((((a + ((a - y) * 0x569dd8f1L)) + b) - c) - d) and 0xffffffffL) or ((d xor ((a - (y * 2L)) + c)) shl 32L.toInt()))
        leaf1 = e
    }
    private fun math_cbd744(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x shr 32L.toInt())
        b = (y and 0xffffffffL)
        c = (a xor ((b shr 11L.toInt()) or (b shl 21L.toInt())))
        d = ((((c + a) + x) + 0xa22aa618L) and 0xffffffffL)
        a = ((a + x) and 0xffffffffL)
        e = (y shr 32L.toInt())
        b = ((((a shr 3L.toInt()) or (a shl 29L.toInt())) + (((y shr 39L.toInt()) and 33554431L) or (e shl 25L.toInt()))) xor b)
        a = (a xor e)
        leaf0 = (d or (((b + 0x418cabc5L) xor c) shl 32L.toInt()))
        leaf1 = (((b + a) and 0xffffffffL) or ((a - ((d shr 4L.toInt()) or (d shl 28L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbd7a0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (((x shr 32L.toInt()) - y) and 0xffffffffL)
        b = (x xor (y shr 32L.toInt()))
        c = (table[((b and 255L) + 370L).toInt()] xor (y and 0xffffffffL))
        b = (((((a shr 28L.toInt()) or (a shl 4L.toInt())) + b) + ((c shr 3L.toInt()) or (c shl 29L.toInt()))) and 0xffffffffL)
        d = ((b shr 28L.toInt()) or (b shl 4L.toInt()))
        e = table[((c and 255L) + 160L).toInt()]
        f = (((d + c) xor (e + a)) and 0xffffffffL)
        a = ((b - e) - a)
        b = (a - ((f shr 17L.toInt()) or (f shl 15L.toInt())))
        e = (b and 0xffffffffL)
        a = (((f + d) + c) + table[((a and 255L) + 241L).toInt()])
        c = ((((e shr 22L.toInt()) or (e shl 10L.toInt())) + 9284L) xor (a + 13767L))
        a = (a and 0xffffffffL)
        b = ((b + ((a shr 12L.toInt()) or (a shl 20L.toInt()))) and 0xffffffffL)
        leaf0 = ((x and 0xffffffffL) or ((f - c) shl 32L.toInt()))
        leaf1 = (((((c + a) - f) xor ((b shr 25L.toInt()) or (b shl 7L.toInt()))) and 0xffffffffL) or ((b + ((c - f) * 0x70266231L)) shl 32L.toInt()))
    }
    private fun math_cbd860(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (((x shr 32L.toInt()) + y) and 0xffffffffL)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = ((y - ((c shr 14L.toInt()) or (c shl 18L.toInt()))) - ((b shr 21L.toInt()) or (b shl 11L.toInt())))
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = ((d and 0xffffffffL) or ((((((b shr 6L.toInt()) or (b shl 26L.toInt())) - 31052L) xor c) xor (d - 2985L)) shl 32L.toInt()))
    }
    private fun math_cbd8e8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = (y and 0xffffffffL)
        c = (a xor b)
        d = (y shr 32L.toInt())
        b = ((b shr 8L.toInt()) or (b shl 24L.toInt()))
        a = (((x shr 60L.toInt()) and 15L) or (a shl 4L.toInt()))
        e = (-(((y shr 51L.toInt()) and 8191L) or (d shl 13L.toInt())))
        f = ((y + e) + c)
        g = ((((f + b) + x) + a) and 0xffffffffL)
        f = (((((-((((g shr 2L.toInt()) or (g shl 30L.toInt())) + 6474L) xor (f - 28645L))) - y) + g) - e) + d)
        g = (f xor ((g + (((y - g) + e) * 439153684L)) - (d * 439153683L)))
        c = (((((g * 0x7b58ce70L) + c) + (((((c + d) + b) + x) + a) * 474994629L)) + (y * 758444090L)) + (e * 758444090L))
        a = (((b + x) + a) and 0xffffffffL)
        b = (((c - (g * 0x7b58ce70L)) + 16655L) xor ((((a shr 2L.toInt()) or (a shl 30L.toInt())) - 23045L) xor f))
        d = (table[((b and 255L) + 101L).toInt()] xor g)
        e = (c - d)
        a = (table[(((c - (g * 112L)) and 255L) + 145L).toInt()] xor a)
        f = ((e xor (a - c)) and 0xffffffffL)
        c = (((c + b) + a) and 0xffffffffL)
        c = (d xor ((c shr 4L.toInt()) or (c shl 28L.toInt())))
        a = ((e + b) + (a * 2L))
        leaf0 = (f or ((e - table[((c and 255L) + 129L).toInt()]) shl 32L.toInt()))
        leaf1 = (((a + c) and 0xffffffffL) or ((a xor (f * (-0x7692b55L))) shl 32L.toInt()))
    }
    private fun math_cbd9fc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x shr 32L.toInt())
        b = (((a + 531353778L) xor x) and 0xffffffffL)
        a = (table[((y and 255L) + 60L).toInt()] xor a)
        c = ((y shr 32L.toInt()) and 0xffffffffL)
        leaf0 = (b or (a shl 32L.toInt()))
        leaf1 = (((y and 0xffffffffL) xor c) or ((((a - 5120L) xor (((b shr 1L.toInt()) or (b shl 31L.toInt())) + 16906L)) + c) shl 32L.toInt()))
    }
    private fun math_cbdab8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        b = ((b shr 20L.toInt()) or (b shl 12L.toInt()))
        c = (x shr 32L.toInt())
        d = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        e = ((d shr 11L.toInt()) or (d shl 21L.toInt()))
        f = ((((((d + y) * 0x6d6e34f4L) + b) + c) + e) and 0xffffffffL)
        d = ((((y * 0x5f027074L) - (f * 0x6c01f861L)) + (d * 0x5f027075L)) xor f)
        b = (y + (((b + c) + e) * 0x6c01f861L))
        c = ((b and 0xffffffffL) xor f)
        e = (d - c)
        f = ((e - 0xb9a9cbfL) xor ((b - d) - c))
        b = (table[((((b - d) - c) and 255L) + 2L).toInt()] xor c)
        c = ((((e + (f * 2L)) + b) + 0x8fc38305L) and 0xffffffffL)
        d = (c xor b)
        b = ((((e + f) + b) - ((c shr 22L.toInt()) or (c shl 10L.toInt()))) - ((d shr 30L.toInt()) or (d shl 2L.toInt())))
        d = (d - ((b + 0x8fc36e8fL) xor (((c shr 23L.toInt()) or (c shl 9L.toInt())) - 24050L)))
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = ((c xor table[(((b + 5L) and 255L) + 505L).toInt()]) or ((d xor (b + 0x8fc38305L)) shl 32L.toInt()))
    }
    private fun math_cbdbac(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x shr 32L.toInt())
        b = (table[((y and 255L) + 215L).toInt()] xor a)
        a = ((x xor (((x shr 58L.toInt()) and 63L) or (a shl 6L.toInt()))) + b)
        c = (y shr 32L.toInt())
        d = (y - c)
        b = (b xor table[((d and 255L) + 228L).toInt()])
        e = (a - b)
        c = ((c - a) and 0xffffffffL)
        d = (d - ((((c shr 4L.toInt()) or (c shl 28L.toInt())) + 24666L) xor (a - 29040L)))
        a = (c xor a)
        leaf0 = ((e and 0xffffffffL) or ((d xor b) shl 32L.toInt()))
        leaf1 = (((d - a) and 0xffffffffL) or (((e + a) + 0x8b0d5e8eL) shl 32L.toInt()))
    }
    private fun math_cbdc38(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (y shr 32L.toInt())
        b = (table[((a and 255L) + 286L).toInt()] xor (y and 0xffffffffL))
        c = (x shr 32L.toInt())
        d = (c xor x)
        e = ((((b shr 15L.toInt()) or (b shl 17L.toInt())) + 8105L) xor ((a - d) - 32545L))
        f = ((e + d) and 0xffffffffL)
        b = (b xor (((d - a) * 464892719L) and 0xffffffffL))
        leaf0 = (f or ((((table[((y and 255L) + 258L).toInt()] xor c) - b) - a) shl 32L.toInt()))
        leaf1 = ((((((-e) - a) * 0x9f87d00L) and 0xffffffffL) xor b) or (((((f shr 8L.toInt()) or (f shl 24L.toInt())) + e) + a) shl 32L.toInt()))
    }
    private fun math_cbdcd4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x and 0xffffffffL)
        b = ((x shr 32L.toInt()) + y)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((y xor ((c shr 19L.toInt()) or (c shl 13L.toInt()))) and 0xffffffffL) or (((b + c) + 0x945313e1L) shl 32L.toInt()))
    }
    private fun math_cbdd08(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (y and 0xffffffffL)
        b = (x xor (y shr 32L.toInt()))
        a = ((((((a shr 29L.toInt()) or (a shl 3L.toInt())) - 20029L) xor (x shr 32L.toInt())) xor (b + 9582L)) and 0xffffffffL)
        c = table[((b and 255L) + 61L).toInt()]
        d = ((a - y) + c)
        a = ((((((a shr 15L.toInt()) or (a shl 17L.toInt())) + 31122L) xor ((y - c) + 30535L)) xor b) and 0xffffffffL)
        b = ((a + y) - c)
        c = ((d - table[((b and 255L) + 101L).toInt()]) and 0xffffffffL)
        a = (a xor table[((d and 255L) + 142L).toInt()])
        leaf0 = ((x and 0xffffffffL) or (c shl 32L.toInt()))
        leaf1 = (((b - ((a shr 31L.toInt()) or (a shl 1L.toInt()))) and 0xffffffffL) or ((a - ((c shr 27L.toInt()) or (c shl 5L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbddb0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = (x shr 32L.toInt())
        b = (((y shr 32L.toInt()) - ((b shr 30L.toInt()) or (b shl 2L.toInt()))) - (((x shr 36L.toInt()) and 0xfffffffL) or (c shl 28L.toInt())))
        d = ((b + y) and 0xffffffffL)
        b = (b and 0xffffffffL)
        c = ((((d shr 16L.toInt()) or (d shl 16L.toInt())) + ((b shr 8L.toInt()) or (b shl 24L.toInt()))) xor c)
        d = (((y + (b * 2L)) - c) and 0xffffffffL)
        leaf0 = (a or ((c - ((d shr 8L.toInt()) or (d shl 24L.toInt()))) shl 32L.toInt()))
        leaf1 = (d or (((b - c) xor a) shl 32L.toInt()))
    }
    private fun math_cbde04(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = ((y shr 32L.toInt()) xor a)
        d = ((c + b) xor c)
        c = ((b - (y * 2L)) - c)
        e = (((((y - b) - d) * 297119187L) xor c) and 0xffffffffL)
        c = (((c + d) + 0xb19a49e3L) and 0xffffffffL)
        b = (((b - y) + d) xor c)
        d = (e - table[((b and 255L) + 204L).toInt()])
        c = ((((b + 10152L) and 0xffffffffL) xor c) xor ((((e shr 11L.toInt()) or (e shl 21L.toInt())) - 30101L) and 0xffffffffL))
        e = ((d and 0xffffffffL) xor c)
        d = ((d - table[(((c + b) and 255L) + 430L).toInt()]) and 0xffffffffL)
        f = (e xor d)
        b = (((table[((e and 255L) + 194L).toInt()] + c) + b) and 0xffffffffL)
        c = ((((f - 7266L) and 0xffffffffL) xor d) xor ((((b shr 18L.toInt()) or (b shl 14L.toInt())) + 16319L) and 0xffffffffL))
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = (((((c + 31878L) xor (((f shr 8L.toInt()) or (f shl 24L.toInt())) + 13926L)) + b) and 0xffffffffL) or ((f - ((c shr 12L.toInt()) or (c shl 20L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbdf08(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x shr 32L.toInt())
        b = (x xor a)
        a = (a xor y)
        c = (y shr 32L.toInt())
        d = (((((y shr 51L.toInt()) and 8191L) or (c shl 13L.toInt())) + 5666L) xor (b - 19594L))
        e = (((b - (a * 2L)) - d) - y)
        c = (table[((b and 255L) + 497L).toInt()] xor c)
        leaf0 = ((e and 0xffffffffL) or ((a - (c * 2L)) shl 32L.toInt()))
        leaf1 = (((((d + y) + (c * 2L)) + 0x8a0b58c8L) and 0xffffffffL) or ((((c - b) + a) xor table[((e and 255L) + 331L).toInt()]) shl 32L.toInt()))
    }
    private fun math_cbdf98(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x and 0xffffffffL)
        b = (y xor (x shr 32L.toInt()))
        c = ((y shr 32L.toInt()) xor a)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = ((((c * (-0x4044e6edL)) xor y) and 0xffffffffL) or (((b + 281575501L) xor c) shl 32L.toInt()))
    }
    private fun math_cbdfd4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (table[((y and 255L) + 90L).toInt()] xor (x shr 32L.toInt()))
        b = (x xor (y shr 32L.toInt()))
        c = (b xor y)
        d = ((a + table[((c and 255L) + 376L).toInt()]) and 0xffffffffL)
        a = (((a * 0x631c838L) xor b) and 0xffffffffL)
        b = (table[((a and 255L) + 317L).toInt()] xor (c and 0xffffffffL))
        c = (d xor ((b shr 12L.toInt()) or (b shl 20L.toInt())))
        a = ((((((d shr 12L.toInt()) or (d shl 20L.toInt())) - 18111L) and 0xffffffffL) xor a) xor ((b + 13198L) and 0xffffffffL))
        b = ((((c - 2286L) and 0xffffffffL) xor b) xor ((((a shr 31L.toInt()) or (a shl 1L.toInt())) - 16371L) and 0xffffffffL))
        leaf0 = ((x and 0xffffffffL) or (c shl 32L.toInt()))
        leaf1 = (b or ((a - ((b - 23472L) xor (c + 30114L))) shl 32L.toInt()))
    }
    private fun math_cbe07c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x shr 32L.toInt())
        b = ((x xor (((x shr 42L.toInt()) and 4194303L) or (a shl 22L.toInt()))) and 0xffffffffL)
        a = ((a + y) and 0xffffffffL)
        c = (y shr 32L.toInt())
        d = (y xor (((y shr 34L.toInt()) and 0x3fffffffL) or (c shl 30L.toInt())))
        e = ((b - ((((a shr 29L.toInt()) or (a shl 3L.toInt())) - 20801L) xor (d - 23043L))) and 0xffffffffL)
        f = (-((a shr 23L.toInt()) or (a shl 9L.toInt())))
        b = (-((b shr 3L.toInt()) or (b shl 29L.toInt())))
        leaf0 = (e or ((d xor a) shl 32L.toInt()))
        leaf1 = (((d - table[((((c + f) + b) and 255L) + 341L).toInt()]) and 0xffffffffL) or ((((table[((e and 255L) + 335L).toInt()] + c) + f) + b) shl 32L.toInt()))
    }
    private fun math_cbe0fc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (y shr 32L.toInt())
        b = (x shr 32L.toInt())
        c = ((a + y) xor b)
        d = ((c + b) + x)
        e = (d and 0xffffffffL)
        a = ((b + x) + a)
        b = ((y - a) and 0xffffffffL)
        leaf0 = (e or ((((a + 11736L) xor c) xor (((b shr 1L.toInt()) or (b shl 31L.toInt())) - 10746L)) shl 32L.toInt()))
        leaf1 = (((d + y) and 0xffffffffL) or ((a xor ((e shr 1L.toInt()) or (e shl 31L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbe14c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x shr 32L.toInt())
        b = (x - a)
        c = (b and 0xffffffffL)
        a = (((y + a) + 0x6b99666bL) and 0xffffffffL)
        d = (c xor (((a shr 3L.toInt()) or (a shl 29L.toInt())) and 0xffffffffL))
        e = (y shr 32L.toInt())
        c = (((((c shr 8L.toInt()) or (c shl 24L.toInt())) + (((y shr 61L.toInt()) and 7L) or (e shl 3L.toInt()))) xor y) and 0xffffffffL)
        b = (b + e)
        e = (c xor (b and 0xffffffffL))
        a = ((a - table[((c and 255L) + 242L).toInt()]) and 0xffffffffL)
        c = ((d - ((e shr 16L.toInt()) or (e shl 16L.toInt()))) - ((a shr 4L.toInt()) or (a shl 28L.toInt())))
        f = ((e shr 28L.toInt()) or (e shl 4L.toInt()))
        b = ((b - ((a + 2462L) xor (((d shr 15L.toInt()) or (d shl 17L.toInt())) + 16455L))) and 0xffffffffL)
        d = ((b shr 13L.toInt()) or (b shl 19L.toInt()))
        e = (b xor e)
        b = (b xor c)
        leaf0 = (((((c - a) - d) - f) and 0xffffffffL) or ((((a + d) + f) xor (e * 560290945L)) shl 32L.toInt()))
        leaf1 = ((((b - 0xc41700aL) and 0xffffffffL) xor e) or (((((a - c) + b) + d) + f) shl 32L.toInt()))
    }
    private fun math_cbe218(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        a = (y shr 32L.toInt())
        b = ((table[((a and 255L) + 327L).toInt()] + y) and 0xffffffffL)
        c = ((x shr 32L.toInt()) and 0xffffffffL)
        d = (y and 0xffffffffL)
        d = ((((a - 22855L) and 0xffffffffL) xor c) xor ((((d shr 31L.toInt()) or (d shl 1L.toInt())) + 22826L) and 0xffffffffL))
        c = (c xor (x and 0xffffffffL))
        a = (((d - 17015L) xor (((c shr 25L.toInt()) or (c shl 7L.toInt())) - 7816L)) + a)
        e = (a and 0xffffffffL)
        f = (((((b shr 14L.toInt()) or (b shl 18L.toInt())) + ((e shr 31L.toInt()) or (e shl 1L.toInt()))) and 0xffffffffL) xor d)
        b = (b xor e)
        e = (f xor table[((b and 255L) + 7L).toInt()])
        c = ((c - ((d shr 18L.toInt()) or (d shl 14L.toInt()))) and 0xffffffffL)
        d = (table[((f and 255L) + 64L).toInt()] xor c)
        a = ((a - ((c shr 2L.toInt()) or (c shl 30L.toInt()))) - ((f shr 24L.toInt()) or (f shl 8L.toInt())))
        c = table[((a and 255L) + 106L).toInt()]
        f = (((e + d) xor ((e + c) + b)) and 0xffffffffL)
        g = (c * (-751333365L))
        h = (b * (-751333365L))
        a = (a xor d)
        i = table[((a and 255L) + 40L).toInt()]
        j = (i * 751333366L)
        k = (f xor ((((g + h) + j) + e) and 0xffffffffL))
        a = (a xor ((((e * 2L) + d) + c) + b))
        b = (((a + c) + b) - i)
        c = (b and 0xffffffffL)
        a = (a - f)
        leaf0 = (k or (((((((((c shr 5L.toInt()) or (c shl 27L.toInt())) + 16528L) xor (a - 24275L)) + g) + j) + e) + h) shl 32L.toInt()))
        leaf1 = (((b - table[((a and 255L) + 247L).toInt()]) and 0xffffffffL) or ((a - k) shl 32L.toInt()))
    }
    private fun math_cbe350(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (y and 0xffffffffL)
        a = (-((a shr 7L.toInt()) or (a shl 25L.toInt())))
        b = (x shr 32L.toInt())
        c = (-(((x shr 34L.toInt()) and 0x3fffffffL) or (b shl 30L.toInt())))
        d = ((((x + a) + c) xor ((b - y) + 0xb66844c8L)) and 0xffffffffL)
        e = (y shr 32L.toInt())
        f = ((((d - b) - e) + 0x4997bb38L) and 0xffffffffL)
        a = ((((e + x) + a) + c) + 0x49b9d7e5L)
        c = (a xor (e + y))
        b = (((b + e) + 0xb66844c8L) and 0xffffffffL)
        a = ((((d shr 12L.toInt()) or (d shl 20L.toInt())) + ((b shr 20L.toInt()) or (b shl 12L.toInt()))) xor a)
        leaf0 = (f or ((b + c) shl 32L.toInt()))
        leaf1 = (((c - a) and 0xffffffffL) or ((a xor ((f shr 15L.toInt()) or (f shl 17L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbe500(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = ((b - y) + c)
        c = (y xor ((c shr 25L.toInt()) or (c shl 7L.toInt())))
        e = (table[((d and 255L) + 136L).toInt()] xor c)
        f = table[((e and 255L) + 126L).toInt()]
        g = ((((((y + (d * 647451223L)) - b) + ((e - f) * 647451225L)) - c) + 0xaba74672L) and 0xffffffffL)
        b = ((((((y - (d * 2L)) - b) - c) + 0xaba74672L) xor ((((c - y) + d) + f) + b)) and 0xffffffffL)
        c = (b xor (((d + e) - f) and 0xffffffffL))
        d = (g xor (((c shr 5L.toInt()) or (c shl 27L.toInt())) and 0xffffffffL))
        b = (b xor (((g shr 11L.toInt()) or (g shl 21L.toInt())) and 0xffffffffL))
        e = ((d * (-0x4092c61eL)) xor b)
        b = ((b shr 25L.toInt()) or (b shl 7L.toInt()))
        f = ((d shr 12L.toInt()) or (d shl 20L.toInt()))
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = (((((d - c) + b) + f) and 0xffffffffL) or ((((c - b) - f) - e) shl 32L.toInt()))
    }
    private fun math_cbe5b4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (b + y)
        d = (c and 0xffffffffL)
        e = (((x shr 32L.toInt()) - (y * 679533843L)) and 0xffffffffL)
        b = (((((d shr 12L.toInt()) or (d shl 20L.toInt())) + ((e shr 6L.toInt()) or (e shl 26L.toInt()))) and 0xffffffffL) xor b)
        c = (c - table[((b and 255L) + 279L).toInt()])
        d = ((d + e) and 0xffffffffL)
        e = (table[((c and 255L) + 460L).toInt()] xor d)
        b = (b xor (((d shr 24L.toInt()) or (d shl 8L.toInt())) and 0xffffffffL))
        c = ((((((b shr 15L.toInt()) or (b shl 17L.toInt())) + 24336L) xor c) xor (e + 30818L)) and 0xffffffffL)
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = (c or ((((((e shr 29L.toInt()) or (e shl 3L.toInt())) + 27563L) xor (c + 12553L)) xor b) shl 32L.toInt()))
    }
    private fun math_cbe664(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        var l = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = (a xor b)
        d = (y shr 32L.toInt())
        b = (d xor b)
        a = (a xor (x and 0xffffffffL))
        d = (d xor ((a shr 19L.toInt()) or (a shl 13L.toInt())))
        a = (a xor c)
        e = ((((((-c) - b) - d) * 0x443791c2L) and 0xffffffffL) xor a)
        f = ((c + (d * 2L)) + a)
        g = (e xor (f and 0xffffffffL))
        h = (a * 0x551fd469L)
        i = ((h + d) and 0xffffffffL)
        b = ((((e shr 11L.toInt()) or (e shl 21L.toInt())) + ((i shr 5L.toInt()) or (i shl 27L.toInt()))) xor ((b - a) - d))
        f = ((f - b) and 0xffffffffL)
        j = (e * 989857078L)
        k = (((b + j) + (i * 989857078L)) and 0xffffffffL)
        l = ((g - ((f shr 24L.toInt()) or (f shl 8L.toInt()))) - ((k shr 3L.toInt()) or (k shl 29L.toInt())))
        a = (((((l + j) - (a * 958604505L)) + (d * 989857080L)) + c) and 0xffffffffL)
        g = ((g shr 3L.toInt()) or (g shl 29L.toInt()))
        j = table[((((e + i) + g) and 255L) + 141L).toInt()]
        f = ((((k * 2L) + f) + j) and 0xffffffffL)
        b = ((((((((((a + e) + j) - d) + g) - c) - (h * 745733080L)) + b) + 429408374L) and 0xffffffffL) or ((((((((a shr 19L.toInt()) or (a shl 13L.toInt())) + ((f shr 1L.toInt()) or (f shl 31L.toInt()))) + e) + l) + i) + g) shl 32L.toInt()))
        leaf0 = (a or (f shl 32L.toInt()))
        leaf1 = b
    }
    private fun math_cbe740(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        b = ((((b shr 31L.toInt()) or (b shl 1L.toInt())) + (x shr 32L.toInt())) + ((c shr 23L.toInt()) or (c shl 9L.toInt())))
        d = table[((b and 255L) + 5L).toInt()]
        leaf0 = (a or ((((b - (c * 2L)) - y) + d) shl 32L.toInt()))
        leaf1 = (((((y * 2L) - b) + (c * 2L)) and 0xffffffffL) or ((((y - b) - (d * 2L)) + (c * 3L)) shl 32L.toInt()))
    }
    private fun math_cbe988(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (y - b)
        d = table[((c and 255L) + 470L).toInt()]
        e = (((x shr 32L.toInt()) xor y) and 0xffffffffL)
        b = (b xor e)
        leaf0 = (a or ((d + e) shl 32L.toInt()))
        leaf1 = (((c and 0xffffffffL) xor b) or (((b - d) - e) shl 32L.toInt()))
    }
    private fun math_cbe9e0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        b = ((((b shr 11L.toInt()) or (b shl 21L.toInt())) + (x shr 32L.toInt())) and 0xffffffffL)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = ((((b shr 31L.toInt()) or (b shl 1L.toInt())) + y) + ((c shr 13L.toInt()) or (c shl 19L.toInt())))
        e = (d xor b)
        b = (((-d) - b) + c)
        c = (-table[(((b - e) and 255L) + 310L).toInt()])
        d = ((b * 0x40073b50L) xor d)
        f = ((d + c) xor table[(((b - d) and 255L) + 461L).toInt()])
        g = (((e + c) + f) and 0xffffffffL)
        b = (((f - b) + d) + ((c + e) * 84804955L))
        leaf0 = (a or (g shl 32L.toInt()))
        leaf1 = (((b + 588658100L) and 0xffffffffL) or (((((g shr 17L.toInt()) or (g shl 15L.toInt())) + 14475L) xor ((b + 588669695L) xor (f - b))) shl 32L.toInt()))
    }
    private fun math_cbeab4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = ((x shr 32L.toInt()) xor ((b shr 1L.toInt()) or (b shl 31L.toInt())))
        d = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        b = (d xor b)
        e = ((((b shr 17L.toInt()) or (b shl 15L.toInt())) + 3749L) xor ((d - c) + 22407L))
        leaf0 = (a or ((c - e) shl 32L.toInt()))
        leaf1 = ((((b + e) - d) and 0xffffffffL) or (((d - (c * 2L)) + e) shl 32L.toInt()))
    }
    private fun math_cbeb00(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (b + y)
        d = (x shr 32L.toInt())
        e = (y and 0xffffffffL)
        e = ((e shr 17L.toInt()) or (e shl 15L.toInt()))
        b = (((c + (d * 2L)) - (e * 2L)) xor b)
        f = ((((b * (-0x74b2131L)) + c) + d) - e)
        c = (((c + d) - e) and 0xffffffffL)
        c = ((b - 25438L) xor (((c shr 30L.toInt()) or (c shl 2L.toInt())) - 12731L))
        b = ((((b - d) + e) + c) and 0xffffffffL)
        g = (f and 0xffffffffL)
        c = ((((d - e) - c) xor ((g shr 15L.toInt()) or (g shl 17L.toInt()))) and 0xffffffffL)
        d = ((f - ((b shr 25L.toInt()) or (b shl 7L.toInt()))) - ((c shr 11L.toInt()) or (c shl 21L.toInt())))
        b = (((c * 0x711f2c40L) and 0xffffffffL) xor b)
        e = ((d and 0xffffffffL) xor b)
        f = ((e shr 8L.toInt()) or (e shl 24L.toInt()))
        c = ((d + c) and 0xffffffffL)
        b = ((b - ((c shr 23L.toInt()) or (c shl 9L.toInt()))) - ((e shr 23L.toInt()) or (e shl 9L.toInt())))
        d = (((b + 0x627549b6L) and 0xffffffffL) xor e)
        leaf0 = (a or ((c + f) shl 32L.toInt()))
        leaf1 = (d or ((((c + d) + f) xor b) shl 32L.toInt()))
    }
    private fun math_cbebbc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (y + (x shr 32L.toInt()))
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = (((b - 0x6ee09feL) and 0xffffffffL) xor c)
        c = ((((c shr 31L.toInt()) or (c shl 1L.toInt())) + y) and 0xffffffffL)
        b = ((((b - ((d shr 8L.toInt()) or (d shl 24L.toInt()))) - ((c shr 7L.toInt()) or (c shl 25L.toInt()))) + 0xf911f602L) and 0xffffffffL)
        e = ((b shr 1L.toInt()) or (b shl 31L.toInt()))
        f = (((((d * 2L) + c) + e) - 959493488L) and 0xffffffffL)
        c = (((d + c) - 959493488L) and 0xffffffffL)
        b = ((((f shr 10L.toInt()) or (f shl 22L.toInt())) + ((c shr 12L.toInt()) or (c shl 20L.toInt()))) xor b)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((((c * 2L) + d) + e) and 0xffffffffL) or ((f + b) shl 32L.toInt()))
    }
    private fun math_cbec38(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (y shr 32L.toInt())
        b = (table[((a and 255L) + 306L).toInt()] xor y)
        c = ((x shr 32L.toInt()) and 0xffffffffL)
        d = (((a + y) and 0xffffffffL) xor c)
        e = (((b + 317499725L) and 0xffffffffL) xor d)
        c = (((x shr 61L.toInt()) and 7L) or (c shl 3L.toInt()))
        f = (e xor (((c + x) - d) and 0xffffffffL))
        a = ((((c + x) * 0x551cb6e9L) + a) and 0xffffffffL)
        g = ((a shr 26L.toInt()) or (a shl 6L.toInt()))
        a = ((((a - x) - e) - c) + d)
        leaf0 = (f or (((e - b) - g) shl 32L.toInt()))
        leaf1 = ((((a + f) xor (b + g)) and 0xffffffffL) or (((f + 0xa13a0748L) xor a) shl 32L.toInt()))
    }
    private fun math_cbecc8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (y shr 32L.toInt())
        b = ((x shr 32L.toInt()) and 0xffffffffL)
        c = (((a + y) and 0xffffffffL) xor b)
        d = (a xor y)
        b = (b xor (x and 0xffffffffL))
        e = (-((((b shr 26L.toInt()) or (b shl 6L.toInt())) + 10030L) xor (c + 29527L)))
        f = (((c + d) + a) + e)
        c = ((c shr 20L.toInt()) or (c shl 12L.toInt()))
        a = ((a + e) and 0xffffffffL)
        e = (a xor (c + b))
        a = (d - ((a shr 29L.toInt()) or (a shl 3L.toInt())))
        d = (a and 0xffffffffL)
        g = (((e + 27414L) xor f) xor (((d shr 30L.toInt()) or (d shl 2L.toInt())) - 31366L))
        h = (((f + c) + b) - g)
        leaf0 = ((h and 0xffffffffL) or ((((g - d) + e) - 0x4d3c3801L) shl 32L.toInt()))
        leaf1 = ((((((h + g) - e) * 879032851L) xor (a - e)) and 0xffffffffL) or (((h + 0x4c84056aL) xor (((e - f) - b) - c)) shl 32L.toInt()))
    }
    private fun math_cbed80(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x shr 32L.toInt())
        b = (((((((x shr 49L.toInt()) and 32767L) or (a shl 15L.toInt())) + 10976L) xor (y - 448L)) + x) and 0xffffffffL)
        c = (y and 0xffffffffL)
        d = (y shr 32L.toInt())
        leaf0 = (b or ((((((c shr 13L.toInt()) or (c shl 19L.toInt())) + 31072L) xor (d + 24374L)) + a) shl 32L.toInt()))
        leaf1 = ((((b + d) and 0xffffffffL) xor c) or ((d - table[((b and 255L) + 214L).toInt()]) shl 32L.toInt()))
    }
    private fun math_cbedf0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = (y shr 32L.toInt())
        c = (((b + y) and 0xffffffffL) xor a)
        d = ((c shr 2L.toInt()) or (c shl 30L.toInt()))
        e = (-(((y shr 49L.toInt()) and 32767L) or (b shl 15L.toInt())))
        b = ((((y + e) - table[(((((a - x) + b) - 9L) and 255L) + 231L).toInt()]) and 0xffffffffL) or ((((((x - a) * 0x3c01eb09L) - (d * 0x3c01eb0aL)) + b) + 0x5e6319f7L) shl 32L.toInt()))
        leaf0 = ((((x - a) - d) and 0xffffffffL) or (((c + y) + e) shl 32L.toInt()))
        leaf1 = b
    }
    private fun math_cbee50(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = (y shr 32L.toInt())
        c = (((y shr 52L.toInt()) and 4095L) or (b shl 12L.toInt()))
        d = ((x - (((x shr 41L.toInt()) and 8388607L) or (a shl 23L.toInt()))) and 0xffffffffL)
        e = ((d shr 24L.toInt()) or (d shl 8L.toInt()))
        a = (((y * 850925706L) + a) and 0xffffffffL)
        b = (((b - ((a shr 22L.toInt()) or (a shl 10L.toInt()))) - e) and 0xffffffffL)
        d = (d xor (((a shr 28L.toInt()) or (a shl 4L.toInt())) and 0xffffffffL))
        f = (((((b shr 28L.toInt()) or (b shl 4L.toInt())) + ((d shr 17L.toInt()) or (d shl 15L.toInt()))) xor ((y - c) - e)) and 0xffffffffL)
        g = (-((f shr 10L.toInt()) or (f shl 22L.toInt())))
        h = (table[((((((((-y) - b) + c) + g) + e) + a) and 255L) + 426L).toInt()] xor ((((((d - y) - b) + c) + a) + e) + 0x46c9dc64L))
        i = (((((((((-f) - y) - (b * 2L)) - (d * 502034873L)) + c) + g) + a) + e) and 0xffffffffL)
        i = ((i shr 11L.toInt()) or (i shl 21L.toInt()))
        leaf0 = (((h - i) and 0xffffffffL) or ((((((((((-h) - (y * 2L)) - (d * 502034872L)) - (b * 3L)) + (c * 2L)) + (e * 2L)) + g) + (a * 2L)) + 939929255L) shl 32L.toInt()))
        leaf1 = ((((((((h - f) - d) + g) * 0x48f84e6bL) - 0x769461cdL) xor (((((((((-h) - y) + f) + d) - b) + c) + a) + e) + 939929255L)) and 0xffffffffL) or ((((((h - f) - d) + ((h - i) * 0x617ece52L)) + g) - 939929255L) shl 32L.toInt()))
    }
    private fun math_cbef30(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = (((b shr 10L.toInt()) or (b shl 22L.toInt())) + (x shr 32L.toInt()))
        d = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        b = (((c + d) and 0xffffffffL) xor b)
        e = (c and 0xffffffffL)
        d = (((((b shr 25L.toInt()) or (b shl 7L.toInt())) + ((e shr 23L.toInt()) or (e shl 9L.toInt()))) and 0xffffffffL) xor d)
        c = ((((b shr 18L.toInt()) or (b shl 14L.toInt())) + ((d shr 22L.toInt()) or (d shl 10L.toInt()))) + c)
        e = (c xor (((-d) - b) * 0x74401debL))
        c = (((c + d) + b) xor d)
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = (((((c * 352211913L) + d) + b) and 0xffffffffL) or ((c + e) shl 32L.toInt()))
    }
    private fun math_cbeff0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (((x shr 32L.toInt()) + y) and 0xffffffffL)
        c = ((y shr 32L.toInt()) xor a)
        d = (((c * 0x5d8357a4L) xor y) and 0xffffffffL)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (d or (((((b shr 24L.toInt()) or (b shl 8L.toInt())) + c) + ((d shr 31L.toInt()) or (d shl 1L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbf02c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (-(y xor ((b shr 28L.toInt()) or (b shl 4L.toInt()))))
        d = (b + c)
        e = (table[((y and 255L) + 433L).toInt()] xor (x shr 32L.toInt()))
        c = ((e + c) and 0xffffffffL)
        c = (-((c shr 16L.toInt()) or (c shl 16L.toInt())))
        f = (((((d * 2L) - e) + c) + 0xa2dcab41L) and 0xffffffffL)
        g = ((f shr 10L.toInt()) or (f shl 22L.toInt()))
        c = ((d - e) + c)
        h = (c and 0xffffffffL)
        b = ((((b - e) + 0xa2dcab41L) xor ((h shr 11L.toInt()) or (h shl 21L.toInt()))) and 0xffffffffL)
        e = ((b shr 19L.toInt()) or (b shl 13L.toInt()))
        b = (b xor f)
        d = ((((d - g) - e) + 0xa2dcab41L) xor table[((b and 255L) + 307L).toInt()])
        c = ((c + g) + e)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = (((b - table[((c and 255L) + 390L).toInt()]) and 0xffffffffL) or ((c xor d) shl 32L.toInt()))
    }
    private fun math_cbf1e8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (y xor (x shr 32L.toInt()))
        c = (b and 0xffffffffL)
        d = ((y shr 32L.toInt()) xor a)
        b = (((d + b) xor y) and 0xffffffffL)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = (b or (((((c shr 17L.toInt()) or (c shl 15L.toInt())) + d) + ((b shr 15L.toInt()) or (b shl 17L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbf21c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (((x shr 32L.toInt()) + y) and 0xffffffffL)
        c = ((y shr 32L.toInt()) xor a)
        d = (y - c)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((d + 0x954a5575L) and 0xffffffffL) or ((c - ((((b shr 10L.toInt()) or (b shl 22L.toInt())) + 14118L) xor (d + 0x954aa816L))) shl 32L.toInt()))
    }
    private fun math_cbf26c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        a = (x and 0xffffffffL)
        b = ((y - 361479757L) xor (x shr 32L.toInt()))
        c = ((y shr 32L.toInt()) xor a)
        d = ((b + c) xor y)
        e = table[((b and 255L) + 40L).toInt()]
        f = (-table[(((c - e) and 255L) + 267L).toInt()])
        g = (d + f)
        h = (g and 0xffffffffL)
        i = ((b - d) and 0xffffffffL)
        i = ((i shr 10L.toInt()) or (i shl 22L.toInt()))
        j = ((h shr 25L.toInt()) or (h shl 7L.toInt()))
        b = (((h shr 27L.toInt()) or (h shl 5L.toInt())) + b)
        d = (b - d)
        k = (((((d + (c * 2L)) - (e * 2L)) - (i * 2L)) - (j * 2L)) and 0xffffffffL)
        c = (((((g - c) + e) + i) + j) and 0xffffffffL)
        e = ((((k shr 23L.toInt()) or (k shl 9L.toInt())) + ((c shr 13L.toInt()) or (c shl 19L.toInt()))) xor ((b - c) + f))
        d = (((e - (c * 3L)) + (d * 2L)) + (h * 4L))
        b = ((((b - c) + h) + f) and 0xffffffffL)
        b = (-((d - 14110L) xor (((b shr 31L.toInt()) or (b shl 1L.toInt())) - 17639L)))
        c = (((b - k) xor d) and 0xffffffffL)
        d = (((-k) - e) and 0xffffffffL)
        b = ((((c shr 6L.toInt()) or (c shl 26L.toInt())) + ((d shr 28L.toInt()) or (d shl 4L.toInt()))) xor (e + b))
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((d + (c * 0x6ba61d4eL)) and 0xffffffffL) or ((b + c) shl 32L.toInt()))
    }
    private fun math_cbf350(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = ((x shr 32L.toInt()) - y)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = ((((b + 8086L) xor y) xor (((c shr 5L.toInt()) or (c shl 27L.toInt())) + 5907L)) and 0xffffffffL)
        e = ((d + 485851411L) xor b)
        b = (table[((b and 255L) + 23L).toInt()] + c)
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = ((table[((b and 255L) + 20L).toInt()] xor d) or ((b xor e) shl 32L.toInt()))
    }
    private fun math_cbf3d4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        a = ((y shr 32L.toInt()) and 0xffffffffL)
        b = (table[((a and 255L) + 327L).toInt()] xor y)
        c = (x shr 32L.toInt())
        a = ((((x - y) - c) and 0xffffffffL) xor a)
        d = (y * 0x669790deL)
        e = (c * (-2L))
        f = ((x + d) + e)
        g = ((((a shr 5L.toInt()) or (a shl 27L.toInt())) + 4044L) xor (f - 12011L))
        h = (((x - g) + b) - a)
        i = (-table[((b and 255L) + 26L).toInt()])
        j = (((y * (-0x669790dfL)) + c) + i)
        k = ((j xor f) and 0xffffffffL)
        f = (k xor ((a - f) and 0xffffffffL))
        a = (((((((((-x) - y) + (h * 2L)) - k) + a) + i) - c) and 0xffffffffL) xor k)
        c = (((((h + d) + e) - k) - ((f shr 26L.toInt()) or (f shl 6L.toInt()))) - ((a shr 15L.toInt()) or (a shl 17L.toInt())))
        b = (((j + b) - g) xor table[(((((h + d) + e) - k) and 255L) + 247L).toInt()])
        d = (((c + b) and 0xffffffffL) xor a)
        e = (c and 0xffffffffL)
        a = (((a * 0x755a5cdfL) + f) and 0xffffffffL)
        leaf0 = (d or (((b - ((e shr 30L.toInt()) or (e shl 2L.toInt()))) - ((a shr 12L.toInt()) or (a shl 20L.toInt()))) shl 32L.toInt()))
        leaf1 = (((c + table[((a and 255L) + 370L).toInt()]) and 0xffffffffL) or ((d xor a) shl 32L.toInt()))
    }
    private fun math_cbf4c0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (((x shr 32L.toInt()) xor y) and 0xffffffffL)
        c = ((y shr 32L.toInt()) xor a)
        d = ((((((-b) - y) - c) * 0x6f8e05c0L) and 0xffffffffL) xor b)
        e = (b + y)
        b = (((e + c) - 22776L) xor (((b shr 7L.toInt()) or (b shl 25L.toInt())) - 25032L))
        c = (((b + c) - ((d shr 11L.toInt()) or (d shl 21L.toInt()))) and 0xffffffffL)
        f = ((c shr 19L.toInt()) or (c shl 13L.toInt()))
        c = ((((c + (b * 3L)) + (d * 5L)) - (e * 3L)) + f)
        leaf0 = (a or (((((b * 2L) + (d * 3L)) - (e * 2L)) + f) shl 32L.toInt()))
        leaf1 = (((c xor (((e - b) - d) - f)) and 0xffffffffL) or ((c + 0x6b590600L) shl 32L.toInt()))
    }
    private fun math_cbf59c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x shr 32L.toInt())
        b = ((x - (((x shr 55L.toInt()) and 511L) or (a shl 9L.toInt()))) and 0xffffffffL)
        a = ((a xor y) and 0xffffffffL)
        c = (((a shr 28L.toInt()) or (a shl 4L.toInt())) and 0xffffffffL)
        d = (b xor c)
        e = (y shr 32L.toInt())
        f = (table[((e and 255L) + 476L).toInt()] + y)
        leaf0 = (d or ((f xor a) shl 32L.toInt()))
        leaf1 = ((((f - (b xor e)) - d) and 0xffffffffL) or ((e xor c) shl 32L.toInt()))
    }
    private fun math_cbf73c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = ((x shr 32L.toInt()) + y)
        b = (a xor x)
        c = ((y shr 32L.toInt()) and 0xffffffffL)
        d = (table[((b and 255L) + 412L).toInt()] xor c)
        leaf0 = ((((b + a) - 0xadd7fccL) and 0xffffffffL) or (((((y + d) + b) + a) + c) shl 32L.toInt()))
        leaf1 = (((((c + y) + b) - ((d shr 17L.toInt()) or (d shl 15L.toInt()))) and 0xffffffffL) or (((((((y + d) + (b * 2L)) + (a * 2L)) + c) - 0xadd7fccL) xor d) shl 32L.toInt()))
    }
    private fun math_cbf7a8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = ((y xor ((b shr 5L.toInt()) or (b shl 27L.toInt()))) and 0xffffffffL)
        d = (((x shr 32L.toInt()) - table[((y and 255L) + 480L).toInt()]) and 0xffffffffL)
        b = ((b - ((c shr 13L.toInt()) or (c shl 19L.toInt()))) - ((d shr 8L.toInt()) or (d shl 24L.toInt())))
        d = (((b + (c * 2L)) xor (((-b) - (c * 2L)) + d)) and 0xffffffffL)
        b = (b + c)
        e = (d - table[((b and 255L) + 376L).toInt()])
        c = ((-c) xor ((d shr 17L.toInt()) or (d shl 15L.toInt())))
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = ((((c + 413084238L) xor b) and 0xffffffffL) or ((c xor e) shl 32L.toInt()))
    }
    private fun math_cbf828(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (x shr 32L.toInt())
        d = table[(((c + y) and 255L) + 447L).toInt()]
        e = ((((b - (y * 757237768L)) + ((b - c) * 378618884L)) - d) + ((b - d) xor (y - b)))
        f = ((((y * 0x5a451012L) - (b * 757237771L)) + (c * 757237769L)) + (d * 2L))
        g = (f and 0xffffffffL)
        h = (e and 0xffffffffL)
        b = ((((b - (y * 757237770L)) + ((b - c) * 378618885L)) - d) xor ((h shr 24L.toInt()) or (h shl 8L.toInt())))
        c = (e - ((((g shr 11L.toInt()) or (g shl 21L.toInt())) + 6098L) xor (b + 11551L)))
        d = (f - b)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = ((d and 0xffffffffL) or ((((d - 12171L) xor b) xor (c - 18095L)) shl 32L.toInt()))
    }
    private fun math_cbfa20(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = ((b shr 30L.toInt()) or (b shl 2L.toInt()))
        d = (a xor (x and 0xffffffffL))
        e = ((((a - c) + 0x52973518L) and 0xffffffffL) xor d)
        d = (d + (y shr 32L.toInt()))
        b = (d xor b)
        leaf0 = (e or ((((d + (a * 2L)) - (c * 2L)) + b) shl 32L.toInt()))
        leaf1 = (((((b - d) + c) - a) and 0xffffffffL) or ((((c - a) - e) - b) shl 32L.toInt()))
    }
    private fun math_cbfa70(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x shr 32L.toInt())
        b = (table[((a and 255L) + 7L).toInt()] xor x)
        c = (y shr 32L.toInt())
        d = (b + c)
        e = table[((d and 255L) + 338L).toInt()]
        c = (-(((y shr 45L.toInt()) and 524287L) or (c shl 19L.toInt())))
        f = (((y + c) xor (a + y)) and 0xffffffffL)
        g = ((((e + y) + c) and 0xffffffffL) xor f)
        d = (d xor table[(((((a * 17L) + (y * 17L)) + b) and 255L) + 366L).toInt()])
        a = (a * 0x496df211L)
        f = table[((f and 255L) + 448L).toInt()]
        c = (((((((d + e) + (y * 0x496df212L)) + c) + a) + b) - f) and 0xffffffffL)
        e = (g xor (((c shr 26L.toInt()) or (c shl 6L.toInt())) and 0xffffffffL))
        g = (((((a + (y * 0x496df211L)) + b) - f) xor (g * (-501667575L))) and 0xffffffffL)
        h = (e xor g)
        a = (((((d - a) - (y * 0x496df211L)) - b) + f) and 0xffffffffL)
        b = (((((g shr 14L.toInt()) or (g shl 18L.toInt())) - 25581L) and 0xffffffffL) xor (((e - 31563L) and 0xffffffffL) xor a))
        a = ((((a shr 12L.toInt()) or (a shl 20L.toInt())) + ((g shr 28L.toInt()) or (g shl 4L.toInt()))) xor c)
        c = (h + table[((((b + a) + e) and 255L) + 484L).toInt()])
        d = (b xor h)
        leaf0 = ((c and 0xffffffffL) or (((a * 2L) + e) shl 32L.toInt()))
        leaf1 = ((((a - b) + ((d shr 20L.toInt()) or (d shl 12L.toInt()))) and 0xffffffffL) or ((d xor table[((c and 255L) + 316L).toInt()]) shl 32L.toInt()))
    }
    private fun math_cbfb80(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = (y and 0xffffffffL)
        c = ((b shr 29L.toInt()) or (b shl 3L.toInt()))
        d = (y shr 32L.toInt())
        b = (d xor b)
        e = ((a - c) xor b)
        a = ((((a * 2L) + x) - c) xor d)
        d = table[((((e - a) - b) and 255L) + 42L).toInt()]
        f = table[((((x + c) - b) and 255L) + 19L).toInt()]
        g = (((((((f + a) + e) + x) + c) - b) + 0x87d799b5L) xor (a + b))
        h = (g * 0x4f831f8fL)
        i = ((((((d + (e * 2L)) + x) + c) - (b * 2L)) + h) - a)
        f = (((((f + (a * 2L)) - (e * 2L)) + (b * 2L)) - x) - c)
        g = (g xor table[(((f + 75L) and 255L) + 415L).toInt()])
        a = ((((h + e) - a) - b) xor table[((g and 255L) + 238L).toInt()])
        h = (((table[((a and 255L) + 100L).toInt()] + i) - 746763283L) and 0xffffffffL)
        b = (((((((d + e) + x) + c) - b) + 0x4f0d4d22L) xor (f + 0x7828664bL)) and 0xffffffffL)
        c = (((i - 746763283L) and 0xffffffffL) xor b)
        leaf0 = (h or (((((c + b) + g) + 0xa0896692L) xor a) shl 32L.toInt()))
        leaf1 = ((c xor (((b + g) + 0xa0896692L) and 0xffffffffL)) or ((c xor h) shl 32L.toInt()))
    }
    private fun math_cbfc90(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x shr 32L.toInt())
        b = (table[((a and 255L) + 68L).toInt()] xor x)
        c = (y and 0xffffffffL)
        c = ((c shr 25L.toInt()) or (c shl 7L.toInt()))
        d = (y shr 32L.toInt())
        e = (b xor d)
        leaf0 = ((((b + a) - c) and 0xffffffffL) or (((((a - c) - e) - y) + d) shl 32L.toInt()))
        leaf1 = (((((((e + y) - d) + b) + a) - c) and 0xffffffffL) or (((((((e * 2L) - b) - (a * 2L)) + (c * 2L)) + y) - d) shl 32L.toInt()))
    }
    private fun math_cbfce8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x shr 32L.toInt())
        b = (x xor a)
        c = (y shr 32L.toInt())
        leaf0 = ((b and 0xffffffffL) or (((y * 0x5e2e179eL) xor a) shl 32L.toInt()))
        leaf1 = (((y - c) and 0xffffffffL) or ((c - b) shl 32L.toInt()))
    }
    private fun math_cbfd18(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = ((b + y) and 0xffffffffL)
        d = ((c shr 22L.toInt()) or (c shl 10L.toInt()))
        e = ((y shr 32L.toInt()) xor a)
        f = (y * 2L)
        c = (table[((((e + f) + b) and 255L) + 158L).toInt()] xor c)
        g = ((c shr 12L.toInt()) or (c shl 20L.toInt()))
        h = ((((e + ((d + e) * 0x86c45ddL)) + f) + b) - g)
        b = ((((((d * 469398682L) - (e * 553731299L)) + (f * 0x43044683L)) - (b * 0x3cfbb97dL)) + c) and 0xffffffffL)
        c = (((h + 0x65395eccL) and 0xffffffffL) xor b)
        b = ((((g + d) + e) xor ((b shr 2L.toInt()) or (b shl 30L.toInt()))) and 0xffffffffL)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = ((((((b shr 18L.toInt()) or (b shl 14L.toInt())) - 15853L) xor ((c + 30538L) xor h)) and 0xffffffffL) or ((b xor ((c shr 1L.toInt()) or (c shl 31L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbfdc4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = ((b shr 21L.toInt()) or (b shl 11L.toInt()))
        d = (x shr 32L.toInt())
        e = ((y shr 32L.toInt()) xor a)
        f = ((((-c) - d) * 0x772b450fL) xor e)
        b = (((e - 497023739L) and 0xffffffffL) xor b)
        e = ((table[((f and 255L) + 47L).toInt()] + b) and 0xffffffffL)
        b = ((c + d) xor ((b shr 21L.toInt()) or (b shl 11L.toInt())))
        c = (f - b)
        b = (((((e shr 20L.toInt()) or (e shl 12L.toInt())) + 19204L) xor (c + 14122L)) + b)
        d = (e xor c)
        e = table[((d and 255L) + 460L).toInt()]
        f = (b and 0xffffffffL)
        c = (c xor ((f shr 20L.toInt()) or (f shl 12L.toInt())))
        leaf0 = (a or ((b + e) shl 32L.toInt()))
        leaf1 = ((((d - c) + 976531470L) and 0xffffffffL) or ((((((c * 2L) - f) - e) - d) - 976531470L) shl 32L.toInt()))
    }
    private fun math_cbfe80(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x xor (y shr 32L.toInt()))
        b = table[((a and 255L) + 454L).toInt()]
        c = ((y - b) and 0xffffffffL)
        d = (y and 0xffffffffL)
        d = ((d shr 9L.toInt()) or (d shl 23L.toInt()))
        e = (x shr 32L.toInt())
        f = (a and 0xffffffffL)
        f = ((f shr 29L.toInt()) or (f shl 3L.toInt()))
        a = ((a - table[((((d + e) + f) and 255L) + 260L).toInt()]) and 0xffffffffL)
        d = (((((((c shr 20L.toInt()) or (c shl 12L.toInt())) + d) + e) + f) + ((a shr 27L.toInt()) or (a shl 5L.toInt()))) and 0xffffffffL)
        e = ((d shr 30L.toInt()) or (d shl 2L.toInt()))
        f = ((a + e) and 0xffffffffL)
        f = ((((f shr 23L.toInt()) or (f shl 9L.toInt())) + a) + c)
        a = (((((y + d) + a) - b) + table[((f and 255L) + 363L).toInt()]) and 0xffffffffL)
        b = (((-d) - c) + e)
        c = (b and 0xffffffffL)
        leaf0 = ((x and 0xffffffffL) or (a shl 32L.toInt()))
        leaf1 = (((f xor ((c shr 14L.toInt()) or (c shl 18L.toInt()))) and 0xffffffffL) or ((b + ((a shr 9L.toInt()) or (a shl 23L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cbfff4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        a = (y shr 32L.toInt())
        b = (y - table[((a and 255L) + 289L).toInt()])
        c = ((x shr 32L.toInt()) and 0xffffffffL)
        d = (c xor (x and 0xffffffffL))
        e = (y and 0xffffffffL)
        c = ((((a + 17777L) and 0xffffffffL) xor c) xor ((((e shr 26L.toInt()) or (e shl 6L.toInt())) - 18895L) and 0xffffffffL))
        a = (((a - ((d shr 22L.toInt()) or (d shl 10L.toInt()))) - ((c shr 12L.toInt()) or (c shl 20L.toInt()))) and 0xffffffffL)
        d = (((b + c) and 0xffffffffL) xor d)
        e = ((b - ((a shr 9L.toInt()) or (a shl 23L.toInt()))) - ((d shr 6L.toInt()) or (d shl 26L.toInt())))
        a = (((d shr 27L.toInt()) or (d shl 5L.toInt())) + a)
        f = ((e + table[((a and 255L) + 444L).toInt()]) and 0xffffffffL)
        g = ((f shr 17L.toInt()) or (f shl 15L.toInt()))
        e = (-table[((e and 255L) + 56L).toInt()])
        b = ((c + (b * 0x7e7f6eedL)) and 0xffffffffL)
        c = ((b shr 29L.toInt()) or (b shl 3L.toInt()))
        a = ((((a - c) - d) - 658322603L) and 0xffffffffL)
        h = ((a shr 18L.toInt()) or (a shl 14L.toInt()))
        c = ((c + d) xor table[(((b + e) and 255L) + 31L).toInt()])
        d = (a xor table[((c and 255L) + 87L).toInt()])
        i = (((a + f) + d) and 0xffffffffL)
        j = ((((b + e) + h) + g) xor i)
        a = (((((((a + b) + f) + e) + h) + g) xor c) + j)
        b = ((i shr 30L.toInt()) or (i shl 2L.toInt()))
        c = (a xor d)
        leaf0 = (((i + a) and 0xffffffffL) or ((j - b) shl 32L.toInt()))
        leaf1 = ((((-a) - c) and 0xffffffffL) or ((((((-i) - a) - j) + c) + b) shl 32L.toInt()))
    }
    private fun math_cc0120(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x shr 32L.toInt())
        b = ((y * 903520235L) xor a)
        c = (y shr 32L.toInt())
        a = (x - a)
        d = (a and 0xffffffffL)
        e = (((y - (((y shr 48L.toInt()) and 65535L) or (c shl 16L.toInt()))) - ((d shr 30L.toInt()) or (d shl 2L.toInt()))) and 0xffffffffL)
        c = (d xor c)
        d = (e xor c)
        e = (((b - ((e shr 18L.toInt()) or (e shl 14L.toInt()))) + d) and 0xffffffffL)
        a = (a - b)
        b = (e xor ((a - 846859022L) and 0xffffffffL))
        a = (a + c)
        leaf0 = (b or (e shl 32L.toInt()))
        leaf1 = (((table[(((a - 14L) and 255L) + 15L).toInt()] + d) and 0xffffffffL) or (((a - b) - 846859022L) shl 32L.toInt()))
    }
    private fun math_cc02c0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (y shr 32L.toInt())
        b = ((x shr 32L.toInt()) and 0xffffffffL)
        c = (table[((y and 255L) + 493L).toInt()] xor b)
        d = (-((((c shr 18L.toInt()) or (c shl 14L.toInt())) + 24154L) xor ((y + a) - 26738L)))
        b = (((((x shr 54L.toInt()) and 1023L) or (b shl 10L.toInt())) - 783L) xor (y - 18928L))
        c = ((c + a) + y)
        e = ((((b + x) + d) xor c) and 0xffffffffL)
        b = ((table[((((a - b) - x) and 255L) + 380L).toInt()] + a) + y)
        f = ((((a + d) + e) xor b) and 0xffffffffL)
        b = (-table[((b and 255L) + 444L).toInt()])
        g = ((((c + f) + b) and 0xffffffffL) xor e)
        a = (((((e shr 29L.toInt()) or (e shl 3L.toInt())) + a) + d) and 0xffffffffL)
        leaf0 = (g or (((c + b) - f) shl 32L.toInt()))
        leaf1 = ((f xor (((a shr 24L.toInt()) or (a shl 8L.toInt())) and 0xffffffffL)) or ((g xor a) shl 32L.toInt()))
    }
    private fun math_cc037c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (x shr 32L.toInt())
        d = (((b + (y * 2L)) + c) and 0xffffffffL)
        e = (((((d shr 20L.toInt()) or (d shl 12L.toInt())) + c) + y) and 0xffffffffL)
        c = ((c + y) and 0xffffffffL)
        b = (((((c shr 20L.toInt()) or (c shl 12L.toInt())) + 12904L) xor (d - 1648L)) xor b)
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = (((table[((b and 255L) + 26L).toInt()] + d) and 0xffffffffL) or ((b xor ((e shr 7L.toInt()) or (e shl 25L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cc03ec(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = ((y xor x) shr 32L.toInt())
        c = (((-(x shr 32L.toInt())) - b) and 0xffffffffL)
        d = (b + ((c shr 4L.toInt()) or (c shl 28L.toInt())))
        e = table[((d and 255L) + 466L).toInt()]
        b = (((b xor y) - d) + e)
        d = (b xor d)
        c = (((d * 0x4855d9e3L) xor (c + e)) and 0xffffffffL)
        d = ((b + d) and 0xffffffffL)
        e = (c xor (((d shr 18L.toInt()) or (d shl 14L.toInt())) and 0xffffffffL))
        leaf0 = (a or (((b - c) - e) shl 32L.toInt()))
        leaf1 = (e or ((d xor a) shl 32L.toInt()))
    }
    private fun math_cc04ac(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = (table[((a and 255L) + 5L).toInt()] + x)
        c = (y shr 32L.toInt())
        d = (((b + c) xor y) and 0xffffffffL)
        a = (((c + y) and 0xffffffffL) xor a)
        e = (d xor a)
        c = (c - b)
        f = (c and 0xffffffffL)
        d = (d - ((f shr 7L.toInt()) or (f shl 25L.toInt())))
        f = (e xor d)
        a = ((b and 0xffffffffL) xor table[((a and 255L) + 291L).toInt()])
        b = (e xor a)
        a = (c - a)
        c = (d xor (a + 0xa92e3cfeL))
        d = (f - c)
        e = ((((f + b) + c) xor d) and 0xffffffffL)
        a = ((a xor ((b shr 4L.toInt()) or (b shl 28L.toInt()))) and 0xffffffffL)
        g = (c xor ((a shr 22L.toInt()) or (a shl 10L.toInt())))
        leaf0 = (e or (((d - g) + 683798371L) shl 32L.toInt()))
        leaf1 = ((((((g + a) - f) - b) - c) and 0xffffffffL) or ((((((a - b) - (c * 2L)) + e) - g) + 683798371L) shl 32L.toInt()))
    }
    private fun math_cc0560(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (y and 0xffffffffL)
        c = (((c shr 13L.toInt()) or (c shl 19L.toInt())) + (x shr 32L.toInt()))
        d = (y - ((((b shr 4L.toInt()) or (b shl 28L.toInt())) - 19578L) xor (c - 4258L)))
        c = (c and 0xffffffffL)
        b = (b xor ((c shr 10L.toInt()) or (c shl 22L.toInt())))
        c = ((d + b) xor c)
        e = ((c * (-0x6ed3d75eL)) xor b)
        c = (((d - b) + e) xor c)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = ((((d - b) - e) and 0xffffffffL) or ((e - table[((c and 255L) + 241L).toInt()]) shl 32L.toInt()))
    }
    private fun math_cc0708(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = ((x shr 32L.toInt()) and 0xffffffffL)
        c = ((b + 0x6b7da39dL) xor (y shr 32L.toInt()))
        d = (c + y)
        e = (d and 0xffffffffL)
        b = (b xor (((e shr 4L.toInt()) or (e shl 28L.toInt())) and 0xffffffffL))
        c = (((b shr 19L.toInt()) or (b shl 13L.toInt())) + c)
        e = (c and 0xffffffffL)
        d = (((d - ((b shr 13L.toInt()) or (b shl 19L.toInt()))) - ((e shr 9L.toInt()) or (e shl 23L.toInt()))) and 0xffffffffL)
        e = ((d shr 6L.toInt()) or (d shl 26L.toInt()))
        c = (((c - b) + e) and 0xffffffffL)
        b = ((b - e) and 0xffffffffL)
        e = ((b shr 3L.toInt()) or (b shl 29L.toInt()))
        f = ((c shr 2L.toInt()) or (c shl 30L.toInt()))
        b = (((((d + b) + f) + e) - 795441803L) and 0xffffffffL)
        c = (c xor ((b shr 22L.toInt()) or (b shl 10L.toInt())))
        d = ((((d + (c * 871204998L)) + f) + e) and 0xffffffffL)
        e = (d xor b)
        c = (e + c)
        f = (c and 0xffffffffL)
        e = (((f shr 19L.toInt()) or (f shl 13L.toInt())) + ((e shr 31L.toInt()) or (e shl 1L.toInt())))
        b = (e xor b)
        f = table[((b and 255L) + 356L).toInt()]
        d = (((e xor d) - c) + f)
        leaf0 = (a or ((table[((d and 255L) + 375L).toInt()] + b) shl 32L.toInt()))
        leaf1 = ((d and 0xffffffffL) or (((c - f) xor a) shl 32L.toInt()))
    }
    private fun math_cc07e4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = ((a + y) xor x)
        c = (table[(((a - y) and 255L) + 357L).toInt()] + b)
        d = (y shr 32L.toInt())
        e = ((y - (d * 0x418f2331L)) and 0xffffffffL)
        f = ((e shr 19L.toInt()) or (e shl 13L.toInt()))
        b = (((d - b) + 0x80f2cd35L) and 0xffffffffL)
        d = ((b shr 10L.toInt()) or (b shl 22L.toInt()))
        e = (((b - 650093350L) and 0xffffffffL) xor e)
        g = ((((((c - f) - a) + y) - d) - e) and 0xffffffffL)
        a = ((((((e shr 3L.toInt()) or (e shl 29L.toInt())) + f) + a) - y) + d)
        c = (-table[((c and 255L) + 165L).toInt()])
        leaf0 = (g or (a shl 32L.toInt()))
        leaf1 = ((((b + c) and 0xffffffffL) xor e) or (((((((g shr 20L.toInt()) or (g shl 12L.toInt())) - 3279L) xor (a + 8752L)) + b) + c) shl 32L.toInt()))
    }
    private fun math_cc089c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x shr 32L.toInt())
        b = ((x - y) - a)
        c = (y shr 32L.toInt())
        leaf0 = ((b and 0xffffffffL) or ((a - table[((y and 255L) + 449L).toInt()]) shl 32L.toInt()))
        leaf1 = ((((c * 909968610L) + y) and 0xffffffffL) or ((table[((b and 255L) + 41L).toInt()] + c) shl 32L.toInt()))
    }
    private fun math_cc08f0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = (y shr 32L.toInt())
        c = table[((b and 255L) + 269L).toInt()]
        d = (a xor x)
        e = table[(((b - d) and 255L) + 32L).toInt()]
        f = (((a - (c * 2L)) + e) + y)
        g = (a * 0x58d21d39L)
        h = (c * (-0x58d21d39L))
        a = (table[(((a - y) and 255L) + 322L).toInt()] xor d)
        i = (((f + 884175767L) xor ((g + h) + a)) and 0xffffffffL)
        a = (((a * 2L) + b) - d)
        b = (((a + g) + h) xor ((e + y) - c))
        c = (((b + 0xa2fb62e8L) xor (f + 0x7241c4daL)) and 0xffffffffL)
        a = (((((a + f) + h) + g) + 0x7241c4daL) and 0xffffffffL)
        leaf0 = (i or (c shl 32L.toInt()))
        leaf1 = (((b - ((a shr 22L.toInt()) or (a shl 10L.toInt()))) and 0xffffffffL) or (((((i shr 21L.toInt()) or (i shl 11L.toInt())) + ((c shr 16L.toInt()) or (c shl 16L.toInt()))) + a) shl 32L.toInt()))
    }
    private fun math_cc0a70(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x shr 32L.toInt())
        b = (y shr 32L.toInt())
        c = (((((y - a) + b) * 0x727a586dL) xor ((a * 0x460dbc0aL) + x)) and 0xffffffffL)
        d = (((((a * 0x460dbc0bL) + x) - b) - y) xor b)
        leaf0 = (c or (((a - (b * 2L)) + 477507669L) shl 32L.toInt()))
        leaf1 = ((table[((d and 255L) + 473L).toInt()] xor (((y - b) + 477507669L) and 0xffffffffL)) or ((d xor c) shl 32L.toInt()))
    }
    private fun math_cc0ae8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = ((b shr 29L.toInt()) or (b shl 3L.toInt()))
        d = ((((table[((y and 255L) + 118L).toInt()] xor (x shr 32L.toInt())) + c) + y) and 0xffffffffL)
        e = ((d shr 26L.toInt()) or (d shl 6L.toInt()))
        b = (b - d)
        c = (((c + y) - table[((b and 255L) + 311L).toInt()]) and 0xffffffffL)
        f = ((((b + e) * 0x782c4a7cL) and 0xffffffffL) xor c)
        b = ((b + e) and 0xffffffffL)
        c = (((((b shr 21L.toInt()) or (b shl 11L.toInt())) + ((c shr 6L.toInt()) or (c shl 26L.toInt()))) and 0xffffffffL) xor d)
        b = (((b + c) + f) and 0xffffffffL)
        c = (((((b shr 23L.toInt()) or (b shl 9L.toInt())) + ((f shr 23L.toInt()) or (f shl 9L.toInt()))) and 0xffffffffL) xor c)
        d = ((f - (b * 0x793341e9L)) and 0xffffffffL)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = (d or ((((d + 13637L) xor b) xor (((c shr 13L.toInt()) or (c shl 19L.toInt())) + 18125L)) shl 32L.toInt()))
    }
    private fun math_cc0bac(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = (a - y)
        c = (y shr 32L.toInt())
        d = (y - (((((y shr 40L.toInt()) and 0xffffffL) or (c shl 24L.toInt())) - 13704L) xor ((x - a) + 0x9f2e171cL)))
        e = (d and 0xffffffffL)
        e = (b - ((e shr 22L.toInt()) or (e shl 10L.toInt())))
        b = (b and 0xffffffffL)
        b = ((((((b shr 15L.toInt()) or (b shl 17L.toInt())) + x) - a) + 0x9f2dfe66L) and 0xffffffffL)
        f = (table[((e and 255L) + 299L).toInt()] xor b)
        g = ((b shr 16L.toInt()) or (b shl 16L.toInt()))
        e = (e and 0xffffffffL)
        h = ((e shr 11L.toInt()) or (e shl 21L.toInt()))
        a = ((((c - x) + a) + 0x60d2019aL) and 0xffffffffL)
        b = ((d - ((a shr 18L.toInt()) or (a shl 14L.toInt()))) - ((b shr 6L.toInt()) or (b shl 26L.toInt())))
        c = (((((a + f) + h) + g) xor b) and 0xffffffffL)
        d = (-((f shr 4L.toInt()) or (f shl 28L.toInt())))
        i = (c xor table[(((((a + h) + d) + g) and 255L) + 455L).toInt()])
        b = ((b + 0x5c41681eL) xor e)
        c = (b - table[((c and 255L) + 415L).toInt()])
        e = (c and 0xffffffffL)
        b = (f + b)
        e = (((i + ((e shr 15L.toInt()) or (e shl 17L.toInt()))) xor b) and 0xffffffffL)
        a = ((((((b + a) + h) + d) + g) + 635274038L) and 0xffffffffL)
        leaf0 = (e or (((c - ((a shr 12L.toInt()) or (a shl 20L.toInt()))) - ((i shr 9L.toInt()) or (i shl 23L.toInt()))) shl 32L.toInt()))
        leaf1 = (((i - a) and 0xffffffffL) or ((e xor a) shl 32L.toInt()))
    }
    private fun math_cc11ac(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (y and 0xffffffffL)
        b = ((a shr 5L.toInt()) or (a shl 27L.toInt()))
        c = (x shr 32L.toInt())
        d = (((x shr 45L.toInt()) and 524287L) or (c shl 19L.toInt()))
        e = ((y shr 32L.toInt()) and 0xffffffffL)
        f = (((((b + x) + d) * 465265831L) and 0xffffffffL) xor e)
        a = (e xor a)
        e = (a xor ((f shr 29L.toInt()) or (f shl 3L.toInt())))
        b = (((((f + b) + x) + d) - c) - y)
        d = table[((b and 255L) + 115L).toInt()]
        g = ((((-f) - (e * 2L)) - d) and 0xffffffffL)
        a = ((a + 0x6d826726L) xor (c + y))
        c = (d + e)
        d = (c and 0xffffffffL)
        e = (((b + a) + e) - ((d shr 10L.toInt()) or (d shl 22L.toInt())))
        h = (e and 0xffffffffL)
        i = (g xor (((h shr 31L.toInt()) or (h shl 1L.toInt())) and 0xffffffffL))
        a = (b xor ((b - f) + a))
        b = ((g shr 7L.toInt()) or (g shl 25L.toInt()))
        e = (-((((g shr 15L.toInt()) or (g shl 17L.toInt())) - 13138L) xor (e + 11041L)))
        leaf0 = (i or ((h + (((d - a) - b) * 560761575L)) shl 32L.toInt()))
        leaf1 = (((((c - b) + e) + 0x5b3db5c5L) and 0xffffffffL) or (((a + e) xor i) shl 32L.toInt()))
    }
    private fun math_cc12fc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x shr 32L.toInt())
        b = ((((y - 5973L) xor x) xor ((((x shr 43L.toInt()) and 2097151L) or (a shl 21L.toInt())) + 11005L)) and 0xffffffffL)
        a = (((a - y) + 0xca22dd41L) and 0xffffffffL)
        c = ((b - ((a shr 25L.toInt()) or (a shl 7L.toInt()))) and 0xffffffffL)
        d = (y shr 32L.toInt())
        b = ((d - ((b shr 31L.toInt()) or (b shl 1L.toInt()))) and 0xffffffffL)
        d = ((y - d) and 0xffffffffL)
        a = ((((b shr 14L.toInt()) or (b shl 18L.toInt())) + ((d shr 7L.toInt()) or (d shl 25L.toInt()))) xor a)
        leaf0 = (c or (a shl 32L.toInt()))
        leaf1 = (((((-c) - b) + d) and 0xffffffffL) or ((b - ((a + 25459L) xor (((c shr 26L.toInt()) or (c shl 6L.toInt())) - 8653L))) shl 32L.toInt()))
    }
    private fun math_cc13f8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = ((x shr 32L.toInt()) xor y)
        d = (y - ((((b shr 24L.toInt()) or (b shl 8L.toInt())) - 7921L) xor (c - 7370L)))
        b = (b xor c)
        c = ((d + b) xor c)
        e = ((d + c) xor c)
        d = ((((e + c) + b) xor (d - b)) and 0xffffffffL)
        b = (((c + b) - e) and 0xffffffffL)
        c = ((((d shr 22L.toInt()) or (d shl 10L.toInt())) + ((b shr 12L.toInt()) or (b shl 20L.toInt()))) xor e)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = (((((-b) - c) + d) and 0xffffffffL) or ((c xor b) shl 32L.toInt()))
    }
    private fun math_cc1478(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        var l = 0L
        a = (y shr 32L.toInt())
        b = (x shr 32L.toInt())
        c = table[((b and 255L) + 8L).toInt()]
        d = ((a - x) + c)
        c = (x - c)
        e = (y and 0xffffffffL)
        f = ((e shr 19L.toInt()) or (e shl 13L.toInt()))
        g = (((y shr 37L.toInt()) and 0x7ffffffL) or (a shl 27L.toInt()))
        h = table[(((((c + f) + b) + g) and 255L) + 262L).toInt()]
        i = ((d - h) and 0xffffffffL)
        e = ((c + a) xor e)
        j = (table[((e and 255L) + 307L).toInt()] xor (((f + b) + g) and 0xffffffffL))
        k = (i xor j)
        e = (e + table[((d and 255L) + 390L).toInt()])
        l = (e xor i)
        a = (((((-(c * 0x6a5ed7e3L)) - (a * 284061982L)) + ((((j - g) - b) - f) * 0x7b4d4901L)) - (l * 284061982L)) + (k * 284061982L))
        b = table[(((e xor j) and 255L) + 472L).toInt()]
        c = ((k shr 26L.toInt()) or (k shl 6L.toInt()))
        e = (((((i - (a * 401197552L)) + b) + k) + (((d - k) + l) * 0x435765e0L)) xor ((((-(a * 761664239L)) - h) + k) + (((d - k) + l) * 360466687L)))
        f = ((((((((e + (a * 40730865L)) - (d * 0x713282c3L)) + h) - (b * 2L)) + c) - (l * 0x713282c2L)) + (k * 0x713282c1L)) and 0xffffffffL)
        leaf0 = (((((a + (h * 284061982L)) + b) - c) and 0xffffffffL) or (((((a * 761664239L) + (e * 0xc764572L)) + h) - (((d - k) + l) * 360466687L)) shl 32L.toInt()))
        leaf1 = (f or ((((((((d * 0x713282c3L) - (a * 40730866L)) - (h * 284061983L)) + b) - (k * 0x713282c1L)) + (l * 0x604411a4L)) + (l * 284061982L)) shl 32L.toInt()))
    }
    private fun math_cc16f8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = ((((y + 29274L) xor (x shr 32L.toInt())) xor (b - 28152L)) and 0xffffffffL)
        d = ((c shr 21L.toInt()) or (c shl 11L.toInt()))
        e = ((y + (b * 2L)) + d)
        c = (((c + y) + b) + 0x547304aaL)
        b = ((d + b) xor c)
        c = (((e + 0x6cb10a83L) xor (b - 27444L)) xor c)
        d = ((e + 0x6cb166a5L) xor b)
        e = ((((c * 3L) - (b * 2L)) - (d * 2L)) and 0xffffffffL)
        f = (((((c * 2L) - b) - d) + 0x536215beL) xor (b - c))
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = (((((table[((f and 255L) + 474L).toInt()] - c) + d) + b) and 0xffffffffL) or ((f - ((e shr 22L.toInt()) or (e shl 10L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cc17b8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = ((x + y) + a)
        c = ((y shr 32L.toInt()) and 0xffffffffL)
        d = ((b and 0xffffffffL) xor c)
        e = (((y - c) + 521847744L) and 0xffffffffL)
        f = (d xor e)
        g = (y and 0xffffffffL)
        a = (((((g shr 8L.toInt()) or (g shl 24L.toInt())) - 1819L) xor (c + 24335L)) xor a)
        c = (-((d shr 2L.toInt()) or (d shl 30L.toInt())))
        e = (-((e shr 30L.toInt()) or (e shl 2L.toInt())))
        g = table[((((((f * 108L) + a) + c) + e) and 255L) + 58L).toInt()]
        h = (((g + b) + c) + e)
        i = (table[((((b - a) + d) and 255L) + 293L).toInt()] xor f)
        b = (((((((a - (h * 0x47482b6eL)) + (g * 0x47482b6dL)) + i) - d) - b) and 0xffffffffL) or (((((table[((h and 255L) + 379L).toInt()] - a) + b) + d) + (((b + c) + e) * 0x47482b6dL)) shl 32L.toInt()))
        leaf0 = ((h and 0xffffffffL) or ((((((f * 509723500L) + a) + c) + e) - i) shl 32L.toInt()))
        leaf1 = b
    }
    private fun math_cc1884(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = (a + x)
        c = (y shr 32L.toInt())
        d = (table[((c and 255L) + 331L).toInt()] xor y)
        e = ((a + y) and 0xffffffffL)
        e = ((b - ((d - 29481L) xor (((e shr 19L.toInt()) or (e shl 13L.toInt())) + 28248L))) and 0xffffffffL)
        a = ((d + a) + y)
        b = ((b + c) and 0xffffffffL)
        c = (d xor b)
        d = ((a + c) and 0xffffffffL)
        f = (e xor d)
        a = (a xor c)
        g = (((f - a) + 0xc0b27f45L) and 0xffffffffL)
        e = (e xor b)
        c = (c xor ((e shr 28L.toInt()) or (e shl 4L.toInt())))
        b = (d xor b)
        leaf0 = (g or ((a - c) shl 32L.toInt()))
        leaf1 = ((((c - ((b shr 7L.toInt()) or (b shl 25L.toInt()))) - ((g shr 26L.toInt()) or (g shl 6L.toInt()))) and 0xffffffffL) or ((((b + c) - f) + 0x3f4d80bbL) shl 32L.toInt()))
    }
    private fun math_cc1934(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = (b - y)
        d = (c xor b)
        c = (table[((d and 255L) + 398L).toInt()] xor (c + ((y shr 32L.toInt()) xor a)))
        e = (c xor b)
        f = ((((-e) - c) - 35044951L) and 0xffffffffL)
        b = (-((c - 11403L) xor ((((x shr 63L.toInt()) and 1L) or (b shl 1L.toInt())) + 3844L)))
        e = (e - table[(((((c + d) + b) + 87L) and 255L) + 4L).toInt()])
        b = (((((((f shr 15L.toInt()) or (f shl 17L.toInt())) - 9517L) xor (e + 5102L)) + c) + d) + b)
        c = (e and 0xffffffffL)
        d = (c xor table[(((b + 87L) and 255L) + 125L).toInt()])
        c = ((((b + 35035043L) xor (((c shr 1L.toInt()) or (c shl 31L.toInt())) + 5393L)) + f) and 0xffffffffL)
        b = (((b + 35044951L) xor ((c shr 3L.toInt()) or (c shl 29L.toInt()))) and 0xffffffffL)
        leaf0 = (a or ((c + d) shl 32L.toInt()))
        leaf1 = ((b xor d) or ((b xor ((c + d) * 962921762L)) shl 32L.toInt()))
    }
    private fun math_cc1a28(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x shr 32L.toInt())
        b = (y shr 32L.toInt())
        c = (((a + x) + b) and 0xffffffffL)
        b = ((b + y) and 0xffffffffL)
        d = (c xor b)
        b = (((c shr 22L.toInt()) or (c shl 10L.toInt())) + ((b shr 12L.toInt()) or (b shl 20L.toInt())))
        c = (((b + a) + x) xor c)
        e = (d - c)
        f = (a xor y)
        d = (((b + f) + d) and 0xffffffffL)
        g = ((e + 0x658dd0bfL) xor d)
        d = ((d shr 27L.toInt()) or (d shl 5L.toInt()))
        h = (f * (-2L))
        f = ((((((b - x) + c) + (f * 2L)) - a) - 0x533b6e4aL) and 0xffffffffL)
        e = (f xor e)
        c = (((((c - f) + d) + 707157098L) and 0xffffffffL) xor f)
        leaf0 = (((((((x - b) + g) + h) + d) + a) and 0xffffffffL) or ((g - e) shl 32L.toInt()))
        leaf1 = (((e - ((c shr 20L.toInt()) or (c shl 12L.toInt()))) and 0xffffffffL) or (((((((x + c) - b) + g) + h) + d) + a) shl 32L.toInt()))
    }
    private fun math_cc1b18(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (y and 0xffffffffL)
        d = ((((c shr 31L.toInt()) or (c shl 1L.toInt())) + (x shr 32L.toInt())) and 0xffffffffL)
        c = (((((b - c) * 603764625L) - 0x565fdf27L) and 0xffffffffL) xor d)
        d = (d xor b)
        b = (((((y - b) - ((d shr 4L.toInt()) or (d shl 28L.toInt()))) - ((c shr 13L.toInt()) or (c shl 19L.toInt()))) + 0xad0bff37L) and 0xffffffffL)
        e = ((b shr 13L.toInt()) or (b shl 19L.toInt()))
        d = ((table[((c and 255L) + 95L).toInt()] + d) and 0xffffffffL)
        leaf0 = (a or ((c - e) shl 32L.toInt()))
        leaf1 = (((b - ((d shr 7L.toInt()) or (d shl 25L.toInt()))) and 0xffffffffL) or ((d + ((e - c) * 452581705L)) shl 32L.toInt()))
    }
    private fun math_cc1bb0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (y xor (x shr 32L.toInt()))
        b = (x xor (y shr 32L.toInt()))
        c = table[((b and 255L) + 157L).toInt()]
        d = table[((a and 255L) + 44L).toInt()]
        a = ((((a - y) + c) - b) + d)
        e = (a and 0xffffffffL)
        b = ((b - d) and 0xffffffffL)
        c = (((((e shr 10L.toInt()) or (e shl 22L.toInt())) + ((b shr 7L.toInt()) or (b shl 25L.toInt()))) xor (y - c)) and 0xffffffffL)
        a = (a + table[((c and 255L) + 115L).toInt()])
        b = (e xor b)
        leaf0 = ((x and 0xffffffffL) or (a shl 32L.toInt()))
        leaf1 = (((((((b shr 27L.toInt()) or (b shl 5L.toInt())) - 23914L) and 0xffffffffL) xor c) xor ((a + 23732L) and 0xffffffffL)) or ((a + b) shl 32L.toInt()))
    }
    private fun math_cc1e08(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = ((y - ((y shr 32L.toInt()) xor a)) and 0xffffffffL)
        d = (((b + y) xor ((c shr 3L.toInt()) or (c shl 29L.toInt()))) and 0xffffffffL)
        e = ((-c) - b)
        f = (e and 0xffffffffL)
        f = ((d - 4625L) xor (((f shr 28L.toInt()) or (f shl 4L.toInt())) + 29513L))
        g = ((f + c) and 0xffffffffL)
        h = ((g shr 13L.toInt()) or (g shl 19L.toInt()))
        g = (-((g - 1691L) xor (((d shr 26L.toInt()) or (d shl 6L.toInt())) + 23762L)))
        e = (e + g)
        f = (table[((e and 255L) + 146L).toInt()] + f)
        c = (((d - h) - table[(((f + c) and 255L) + 115L).toInt()]) and 0xffffffffL)
        e = (c xor ((e - d) + h))
        b = ((((f + g) - d) - b) + h)
        c = (c xor table[((b and 255L) + 215L).toInt()])
        b = ((e + c) xor b)
        d = ((b + 0x982cf918L) xor c)
        c = (((((c shr 3L.toInt()) or (c shl 29L.toInt())) - 2013L) xor (b + 17625L)) xor e)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = (((b - c) and 0xffffffffL) or ((c - d) shl 32L.toInt()))
    }
    private fun math_cc1ef8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (y and 0xffffffffL)
        d = (((b - 11495L) xor (((c shr 16L.toInt()) or (c shl 16L.toInt())) + 2771L)) + (x shr 32L.toInt()))
        e = ((b shr 26L.toInt()) or (b shl 6L.toInt()))
        f = (d and 0xffffffffL)
        f = ((f shr 14L.toInt()) or (f shl 18L.toInt()))
        d = ((((d + b) + y) - e) - f)
        g = (table[((d and 255L) + 195L).toInt()] xor (((y - e) - f) and 0xffffffffL))
        b = ((((f * 2L) + (e * 2L)) - (c * 2L)) - b)
        c = (table[((g and 255L) + 257L).toInt()] xor b)
        b = (((b + 0x8b9420a9L) xor d) and 0xffffffffL)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = ((g xor (((b shr 11L.toInt()) or (b shl 21L.toInt())) and 0xffffffffL)) or (((c - 862862694L) xor b) shl 32L.toInt()))
    }
    private fun math_cc2020(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x shr 32L.toInt())
        b = table[((a and 255L) + 33L).toInt()]
        c = (((b + x) xor ((a + y) + 0xb872e32L)) and 0xffffffffL)
        d = (y shr 32L.toInt())
        e = (y xor (((y shr 47L.toInt()) and 131071L) or (d shl 17L.toInt())))
        a = (((e + a) + y) and 0xffffffffL)
        f = (c xor a)
        b = ((d - b) - x)
        c = (b + c)
        d = (c and 0xffffffffL)
        leaf0 = (f or ((table[(((b + e) and 255L) + 20L).toInt()] xor a) shl 32L.toInt()))
        leaf1 = ((((((d shr 31L.toInt()) or (d shl 1L.toInt())) + b) + e) and 0xffffffffL) or ((c - ((f shr 23L.toInt()) or (f shl 9L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cc209c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (y and 0xffffffffL)
        b = ((a shr 17L.toInt()) or (a shl 15L.toInt()))
        c = (x shr 32L.toInt())
        d = (((x shr 47L.toInt()) and 131071L) or (c shl 17L.toInt()))
        e = (y shr 32L.toInt())
        leaf0 = ((((b + x) + d) and 0xffffffffL) or (((((a shr 31L.toInt()) or (a shl 1L.toInt())) + c) + (((y shr 54L.toInt()) and 1023L) or (e shl 10L.toInt()))) shl 32L.toInt()))
        leaf1 = ((table[((e and 255L) + 467L).toInt()] xor a) or ((((((-b) - x) - d) * 0x530e2bbcL) + e) shl 32L.toInt()))
    }
    private fun math_cc20ec(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = (y + a)
        c = (y shr 32L.toInt())
        a = (a xor x)
        d = (y xor (((y shr 38L.toInt()) and 67108863L) or (c shl 26L.toInt())))
        e = ((((b + a) + d) + 621531993L) and 0xffffffffL)
        f = (-((e shr 11L.toInt()) or (e shl 21L.toInt())))
        g = (((b - c) + a) + f)
        h = (((e - (d * 2L)) - c) and 0xffffffffL)
        e = ((((h shr 4L.toInt()) or (h shl 28L.toInt())) + e) and 0xffffffffL)
        h = (table[(((g + 89L) and 255L) + 185L).toInt()] xor e)
        i = (((c - a) + f) and 0xffffffffL)
        a = ((((((-d) - g) + h) + c) - a) - ((i shr 29L.toInt()) or (i shl 3L.toInt())))
        c = (((g - h) + 621531993L) and 0xffffffffL)
        d = (((a - 621531993L) and 0xffffffffL) xor c)
        e = ((e shr 29L.toInt()) or (e shl 3L.toInt()))
        g = ((g + 621531993L) and 0xffffffffL)
        g = ((g shr 11L.toInt()) or (g shl 21L.toInt()))
        i = ((((i + h) + g) + e) and 0xffffffffL)
        b = ((((((((-((i shr 28L.toInt()) or (i shl 4L.toInt()))) - a) + h) - g) - e) - (f * 2L)) - b) and 0xffffffffL)
        a = (i xor table[(((a - 89L) and 255L) + 405L).toInt()])
        leaf0 = (d or ((b xor c) shl 32L.toInt()))
        leaf1 = ((b xor a) or ((a - d) shl 32L.toInt()))
    }
    private fun math_cc21ac(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = (-table[((a and 255L) + 436L).toInt()])
        c = (y shr 32L.toInt())
        d = (table[((c and 255L) + 271L).toInt()] xor (y and 0xffffffffL))
        e = (d xor ((a - y) and 0xffffffffL))
        f = (((((x + b) - a) + y) xor ((e shr 16L.toInt()) or (e shl 16L.toInt()))) and 0xffffffffL)
        c = (((x + b) * 0x503c209cL) xor c)
        d = (c xor d)
        e = ((e - d) and 0xffffffffL)
        g = (f xor (((e shr 22L.toInt()) or (e shl 10L.toInt())) and 0xffffffffL))
        a = (((((c + x) + b) - a) + y) and 0xffffffffL)
        b = (((a shr 8L.toInt()) or (a shl 24L.toInt())) + d)
        a = (f xor a)
        c = ((b + a) xor e)
        d = (g + table[((c and 255L) + 406L).toInt()])
        b = (b xor ((a shr 30L.toInt()) or (a shl 2L.toInt())))
        e = (c xor b)
        f = ((d - table[((e and 255L) + 493L).toInt()]) and 0xffffffffL)
        a = (a - ((((g shr 28L.toInt()) or (g shl 4L.toInt())) + 16146L) xor (c - 20672L)))
        c = (a and 0xffffffffL)
        b = ((b - ((c shr 24L.toInt()) or (c shl 8L.toInt()))) and 0xffffffffL)
        a = (a - table[((d and 255L) + 67L).toInt()])
        leaf0 = (f or ((b xor e) shl 32L.toInt()))
        leaf1 = ((b xor ((a + 0x55a83d7aL) and 0xffffffffL)) or ((a xor ((f shr 10L.toInt()) or (f shl 22L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cc2290(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = y
        d = (b xor c)
        c = (-((x shr 32L.toInt()) xor c))
        e = (((b + c) + 0x7d7f7e50L) xor d)
        f = ((d + b) - e)
        b = (((d + (b * 2L)) + c) and 0xffffffffL)
        leaf0 = (a or (f shl 32L.toInt()))
        leaf1 = (((e - ((b shr 25L.toInt()) or (b shl 7L.toInt()))) and 0xffffffffL) or ((f xor b) shl 32L.toInt()))
    }
    private fun math_cc22e0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (x shr 32L.toInt())
        d = ((y - ((b shr 4L.toInt()) or (b shl 28L.toInt()))) and 0xffffffffL)
        e = ((c + y) - ((d shr 3L.toInt()) or (d shl 29L.toInt())))
        f = (e and 0xffffffffL)
        b = ((((y + d) + b) + c) and 0xffffffffL)
        c = table[((b and 255L) + 498L).toInt()]
        d = ((((c - ((f shr 7L.toInt()) or (f shl 25L.toInt()))) - f) + d) and 0xffffffffL)
        c = (d xor (e - c))
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = ((b xor d) or ((b + table[((c and 255L) + 375L).toInt()]) shl 32L.toInt()))
    }
    private fun math_cc2360(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = (y and 0xffffffffL)
        b = ((((((x shr 38L.toInt()) and 67108863L) or (a shl 26L.toInt())) + ((b shr 3L.toInt()) or (b shl 29L.toInt()))) xor x) and 0xffffffffL)
        c = ((b shr 2L.toInt()) or (b shl 30L.toInt()))
        d = ((a + y) and 0xffffffffL)
        e = ((d shr 19L.toInt()) or (d shl 13L.toInt()))
        f = (y shr 32L.toInt())
        g = ((c + e) + f)
        b = ((b - d) and 0xffffffffL)
        d = ((g + ((b shr 5L.toInt()) or (b shl 27L.toInt()))) and 0xffffffffL)
        h = (g and 0xffffffffL)
        h = ((y - f) - ((h shr 5L.toInt()) or (h shl 27L.toInt())))
        i = (h and 0xffffffffL)
        c = ((((a - c) - e) - ((d shr 20L.toInt()) or (d shl 12L.toInt()))) - ((i shr 31L.toInt()) or (i shl 1L.toInt())))
        a = ((b + (((f - g) + a) * 881886977L)) and 0xffffffffL)
        b = (table[((c and 255L) + 438L).toInt()] xor a)
        e = ((h + ((d shr 24L.toInt()) or (d shl 8L.toInt()))) and 0xffffffffL)
        a = (a xor d)
        c = ((c - ((e shr 29L.toInt()) or (e shl 3L.toInt()))) - ((a shr 19L.toInt()) or (a shl 13L.toInt())))
        d = (b xor c)
        e = (e xor a)
        f = (((c + d) - e) and 0xffffffffL)
        b = (-table[((b and 255L) + 80L).toInt()])
        g = (((a + b) and 0xffffffffL) xor e)
        a = ((b - (d * 317612791L)) + a)
        b = (a and 0xffffffffL)
        leaf0 = (f or (((g * 939226899L) xor (c - e)) shl 32L.toInt()))
        leaf1 = (((((f - 2712L) and 0xffffffffL) xor g) xor ((((b shr 5L.toInt()) or (b shl 27L.toInt())) - 28676L) and 0xffffffffL)) or ((a - ((f shr 28L.toInt()) or (f shl 4L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cc2470(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = (((b - y) - 271318121L) and 0xffffffffL)
        d = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        e = ((((c shr 14L.toInt()) or (c shl 18L.toInt())) + ((d shr 4L.toInt()) or (d shl 28L.toInt()))) xor y)
        f = (e * 2L)
        d = (c xor d)
        g = ((d shr 3L.toInt()) or (d shl 29L.toInt()))
        h = (e + g)
        e = ((e + c) and 0xffffffffL)
        d = (((((e shr 3L.toInt()) or (e shl 29L.toInt())) - 9703L) xor (h - 12341L)) + d)
        e = (h - table[((d and 255L) + 245L).toInt()])
        h = (e and 0xffffffffL)
        c = (((f + c) + g) xor ((h shr 23L.toInt()) or (h shl 9L.toInt())))
        b = ((((d + f) + b) - y) + g)
        d = table[(((b + 151L) and 255L) + 37L).toInt()]
        b = (((b - c) - 271318121L) and 0xffffffffL)
        f = (-((b shr 22L.toInt()) or (b shl 10L.toInt())))
        leaf0 = (a or (((c - h) + d) shl 32L.toInt()))
        leaf1 = ((((e - d) + f) and 0xffffffffL) or (((c + f) xor b) shl 32L.toInt()))
    }
    private fun math_cc2598(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = ((y shr 32L.toInt()) xor a)
        d = (((b * 2L) - (c * 4L)) - (y * 3L))
        e = (d and 0xffffffffL)
        b = (((c * 2L) - b) + y)
        f = (b and 0xffffffffL)
        c = ((((e shr 7L.toInt()) or (e shl 25L.toInt())) + ((f shr 29L.toInt()) or (f shl 3L.toInt()))) xor (c + y))
        d = (d - c)
        f = (d and 0xffffffffL)
        b = ((b - ((e shr 10L.toInt()) or (e shl 22L.toInt()))) and 0xffffffffL)
        leaf0 = (a or (f shl 32L.toInt()))
        leaf1 = ((((c - ((b shr 1L.toInt()) or (b shl 31L.toInt()))) - ((f shr 30L.toInt()) or (f shl 2L.toInt()))) and 0xffffffffL) or ((table[((d and 255L) + 361L).toInt()] xor b) shl 32L.toInt()))
    }
    private fun math_cc2618(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x shr 32L.toInt())
        b = (table[((y and 255L) + 361L).toInt()] xor a)
        c = (y shr 32L.toInt())
        d = (c xor y)
        a = (table[((a and 255L) + 392L).toInt()] + x)
        e = (a and 0xffffffffL)
        e = ((((e shr 29L.toInt()) or (e shl 3L.toInt())) - 13044L) xor (b + 28311L))
        f = ((((b - d) - e) - c) and 0xffffffffL)
        g = (((d - e) - c) and 0xffffffffL)
        a = (a - b)
        g = (((((f shr 7L.toInt()) or (f shl 25L.toInt())) + ((g shr 12L.toInt()) or (g shl 20L.toInt()))) xor a) and 0xffffffffL)
        h = (g - table[(((b - (d * 2L)) and 255L) + 421L).toInt()])
        a = table[((a and 255L) + 431L).toInt()]
        g = ((g shr 11L.toInt()) or (g shl 21L.toInt()))
        c = (((g + e) + c) - (a * 2L))
        e = (h and 0xffffffffL)
        e = (-((e shr 3L.toInt()) or (e shl 29L.toInt())))
        h = ((((c + h) - b) + e) + (d * 3L))
        c = ((h xor (c + d)) and 0xffffffffL)
        f = (((((((h - c) - b) - (a * 2L)) + (d * 4L)) - e) and 0xffffffffL) or (((((((((c shr 30L.toInt()) or (c shl 2L.toInt())) - 25587L) xor (h - 9884L)) + f) + (a * 3L)) - (d * 3L)) - g) shl 32L.toInt()))
        leaf0 = f
        leaf1 = (((c - h) and 0xffffffffL) or ((((((a * 2L) + e) + b) - (d * 4L)) + c) shl 32L.toInt()))
    }
    private fun math_cc285c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        var l = 0L
        var m = 0L
        a = (y shr 32L.toInt())
        b = (x - y)
        c = (x shr 32L.toInt())
        d = ((b - c) and 0xffffffffL)
        e = ((d shr 21L.toInt()) or (d shl 11L.toInt()))
        f = (e * (-0x6990d4ceL))
        g = table[((y and 255L) + 31L).toInt()]
        c = ((c - g) and 0xffffffffL)
        h = ((c shr 1L.toInt()) or (c shl 31L.toInt()))
        i = (h * (-0x6990d4ceL))
        j = table[((((((-g) - y) + a) + b) and 255L) + 365L).toInt()]
        k = (((((((d * 2L) - y) - h) - e) + (a * 2L)) + 0x4ec5a28cL) and 0xffffffffL)
        c = ((j - c) + d)
        l = (-((((k shr 25L.toInt()) or (k shl 7L.toInt())) + 10523L) xor (c + 0x4ec5f05fL)))
        m = (((((y + l) + ((i + f) * 0x7fffffffL)) - d) - (a * 0x6990d4cfL)) and 0xffffffffL)
        c = table[(((c + 140L) and 255L) + 407L).toInt()]
        b = ((((((((j - (y * 2L)) + i) + f) + (a * 0x6990d4d0L)) + (d * 3L)) + 0x4ec5a28cL) and 0xffffffffL) or ((((((((-y) - ((((m shr 9L.toInt()) or (m shl 23L.toInt())) + 22529L) xor ((k + c) + 10308L))) - m) + l) - g) + a) + b) shl 32L.toInt()))
        a = ((((((((l - j) - c) + (k * 752768611L)) - (d * 0x59bcacccL)) + ((y - a) * 752768615L)) - 0x7dfc293cL) and 0xffffffffL) or ((((((y - d) - a) + (((e + h) - a) * 0x6990d4cdL)) + c) - j) shl 32L.toInt()))
        leaf0 = b
        leaf1 = a
    }
    private fun math_cc2944(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        var j = 0L
        var k = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (x shr 32L.toInt())
        d = (c * 2L)
        e = ((b + y) and 0xffffffffL)
        f = ((e shr 5L.toInt()) or (e shl 27L.toInt()))
        b = ((((b * 2L) + y) + c) and 0xffffffffL)
        g = ((b shr 30L.toInt()) or (b shl 2L.toInt()))
        h = (((((((e * 3L) - y) - g) - f) + d) and 0xffffffffL) xor e)
        i = ((h shr 22L.toInt()) or (h shl 10L.toInt()))
        j = (f * (-2L))
        k = (g * (-2L))
        d = (table[(((((((h - e) + i) + g) + f) - c) and 255L) + 392L).toInt()] xor ((((((e * 2L) + d) + j) + k) - (i * 2L)) - h))
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = ((((((((y - (e * 5L)) - (c * 4L)) + (f * 3L)) + (g * 3L)) + (i * 2L)) + h) and 0xffffffffL) or ((d xor (((((y + (b * 2L)) + k) + j) - i) + c)) shl 32L.toInt()))
    }
    private fun math_cc29d0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        b = (((x shr 32L.toInt()) xor ((b shr 21L.toInt()) or (b shl 11L.toInt()))) and 0xffffffffL)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = ((y - c) and 0xffffffffL)
        c = (((((d shr 6L.toInt()) or (d shl 26L.toInt())) + ((b shr 14L.toInt()) or (b shl 18L.toInt()))) and 0xffffffffL) xor c)
        b = ((b - ((d shr 18L.toInt()) or (d shl 14L.toInt()))) - ((c shr 9L.toInt()) or (c shl 23L.toInt())))
        e = (b and 0xffffffffL)
        f = (e xor d)
        d = (c xor d)
        b = (b - ((d shr 28L.toInt()) or (d shl 4L.toInt())))
        c = ((c xor e) xor table[((b and 255L) + 158L).toInt()])
        d = ((f - ((c shr 28L.toInt()) or (c shl 4L.toInt()))) and 0xffffffffL)
        e = (d xor (b + f))
        b = ((c - b) - f)
        c = (d xor table[((b and 255L) + 244L).toInt()])
        d = ((e - ((c shr 22L.toInt()) or (c shl 10L.toInt()))) and 0xffffffffL)
        b = ((e + 0xab86327dL) xor b)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = (((b + c) and 0xffffffffL) or ((((d shr 29L.toInt()) or (d shl 3L.toInt())) + b) shl 32L.toInt()))
    }
    private fun math_cc2a94(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = (a + y)
        a = (a xor x)
        c = (b + a)
        d = (y shr 32L.toInt())
        a = (d - a)
        e = (table[((c and 255L) + 148L).toInt()] xor (a and 0xffffffffL))
        f = ((d - 807067134L) xor y)
        a = (e xor (((a + f) + 0xa2a66ef8L) and 0xffffffffL))
        g = ((a shr 23L.toInt()) or (a shl 9L.toInt()))
        d = (((c - f) - d) and 0xffffffffL)
        c = (c xor ((d shr 15L.toInt()) or (d shl 17L.toInt())))
        b = (((((e - d) + b) - 0x5d599108L) and 0xffffffffL) xor d)
        d = ((b shr 12L.toInt()) or (b shl 20L.toInt()))
        leaf0 = ((((g + c) + d) and 0xffffffffL) or ((b - a) shl 32L.toInt()))
        leaf1 = (((a + table[(((e - c) and 255L) + 120L).toInt()]) and 0xffffffffL) or (((e - c) + ((((-c) - d) - g) * 886352152L)) shl 32L.toInt()))
    }
    private fun math_cc2b80(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x shr 32L.toInt())
        b = (y shr 32L.toInt())
        c = (((table[(((((a * (-70L)) - (x * 70L)) + y) and 255L) + 452L).toInt()] + a) - (y * 2L)) + b)
        d = (((a - y) + 0xe53ebd20L) and 0xffffffffL)
        e = ((y - b) and 0xffffffffL)
        f = ((((d shr 20L.toInt()) or (d shl 12L.toInt())) + ((e shr 2L.toInt()) or (e shl 30L.toInt()))) xor (a + x))
        d = (((d - e) - 0x6af47c36L) and 0xffffffffL)
        a = (((-a) - x) * 955387974L)
        d = (f - ((((d shr 26L.toInt()) or (d shl 6L.toInt())) + 17626L) xor ((y + a) - 27797L)))
        e = (((c - 930516717L) xor d) and 0xffffffffL)
        b = (((f + 0xb98155c9L) xor (a + b)) and 0xffffffffL)
        a = ((((b shr 3L.toInt()) or (b shl 29L.toInt())) + y) + a)
        b = ((d + b) and 0xffffffffL)
        leaf0 = (e or (((c + 0x7a4a40eaL) xor (b + a)) shl 32L.toInt()))
        leaf1 = (((a and 0xffffffffL) xor b) or ((e xor b) shl 32L.toInt()))
    }
    private fun math_cc2c58(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (x shr 32L.toInt())
        d = ((((b * 3L) + (y * 3L)) + (c * 2L)) xor b)
        e = (((((((-b) - y) - c) - d) xor (((d * 444957028L) + ((b + y) * 0x4f90842cL)) + (c * 889914056L))) and 0xffffffffL) or ((((((-b) - y) * 77496861L) - (c * 0x4376e196L)) + (d * 0x3ed85f79L)) shl 32L.toInt()))
        leaf0 = (a or (((((b * 4L) + (y * 4L)) + (c * 3L)) + d) shl 32L.toInt()))
        leaf1 = e
    }
    private fun math_cc2cb0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (b xor (y and 0xffffffffL))
        d = ((b + y) xor (x shr 32L.toInt()))
        b = (c - (d xor b))
        d = (d + ((c shr 9L.toInt()) or (c shl 23L.toInt())))
        c = ((d + c) and 0xffffffffL)
        d = ((b xor d) and 0xffffffffL)
        b = ((b - ((c shr 25L.toInt()) or (c shl 7L.toInt()))) - ((d shr 31L.toInt()) or (d shl 1L.toInt())))
        c = (c + d)
        e = (b xor c)
        f = table[((e and 255L) + 443L).toInt()]
        b = (b xor d)
        c = ((b + e) xor c)
        d = (e - c)
        c = (d xor c)
        e = (((((f * 2L) - d) - c) + (b * 2L)) and 0xffffffffL)
        b = (((d - f) - b) - c)
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = ((b and 0xffffffffL) or ((((b - 22767L) xor c) xor (((e shr 1L.toInt()) or (e shl 31L.toInt())) + 23423L)) shl 32L.toInt()))
    }
    private fun math_cc2db0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = ((y shr 32L.toInt()) and 0xffffffffL)
        b = (((y shr 52L.toInt()) and 4095L) or (a shl 12L.toInt()))
        c = (x shr 32L.toInt())
        d = (c xor y)
        c = (((c + x) + d) and 0xffffffffL)
        a = (c xor a)
        e = ((a shr 12L.toInt()) or (a shl 20L.toInt()))
        d = (((((y * 2L) - (b * 2L)) + d) + a) + e)
        f = ((d and 0xffffffffL) xor c)
        a = ((a - c) and 0xffffffffL)
        leaf0 = (f or ((d + 0x6caf6740L) shl 32L.toInt()))
        leaf1 = (((((e + y) - b) xor ((a shr 7L.toInt()) or (a shl 25L.toInt()))) and 0xffffffffL) or ((((f shr 25L.toInt()) or (f shl 7L.toInt())) + a) shl 32L.toInt()))
    }
    private fun math_cc2e10(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = (((y * (-913054607L)) and 0xffffffffL) xor a)
        c = (y shr 32L.toInt())
        d = (-(((y shr 37L.toInt()) and 0x7ffffffL) or (c shl 27L.toInt())))
        a = (x - a)
        e = (a and 0xffffffffL)
        f = (-((e shr 13L.toInt()) or (e shl 19L.toInt())))
        g = (((b + y) + d) + f)
        e = (e xor b)
        h = (table[(((g + 31L) and 255L) + 494L).toInt()] xor e)
        a = (table[((a and 255L) + 83L).toInt()] + c)
        c = table[((a and 255L) + 443L).toInt()]
        b = ((((b - g) + c) * 0x4ff06b03L) xor (g + 0x491ff71fL))
        e = ((e shr 17L.toInt()) or (e shl 15L.toInt()))
        leaf0 = (h or (b shl 32L.toInt()))
        leaf1 = (((((h + a) + e) xor (((y + d) + f) - c)) and 0xffffffffL) or (((b + h) xor (a + e)) shl 32L.toInt()))
    }
    private fun math_cc2ec4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x shr 32L.toInt())
        b = (((((x shr 35L.toInt()) and 536870911L) or (a shl 29L.toInt())) - 20018L) xor (y - 30569L))
        c = (y and 0xffffffffL)
        d = (y shr 32L.toInt())
        leaf0 = (((x - b) and 0xffffffffL) or ((a - ((((c shr 27L.toInt()) or (c shl 5L.toInt())) + 4245L) xor (d + 7859L))) shl 32L.toInt()))
        leaf1 = ((((d * (-0x3d6f4790L)) and 0xffffffffL) xor c) or ((((b - x) + d) - 75482139L) shl 32L.toInt()))
    }
    private fun math_cc2f30(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = ((b shr 7L.toInt()) or (b shl 25L.toInt()))
        d = (x shr 32L.toInt())
        e = (d * (-0x4957e450L))
        d = (((((c + (y * 0x4957e451L)) + e) + b) xor (d - y)) and 0xffffffffL)
        f = (d xor (((e + (y * 0x4957e450L)) + b) and 0xffffffffL))
        b = ((table[(((((y * 80L) + e) + b) and 255L) + 193L).toInt()] + c) + y)
        c = (b and 0xffffffffL)
        d = ((((f shr 8L.toInt()) or (f shl 24L.toInt())) + d) + ((c shr 12L.toInt()) or (c shl 20L.toInt())))
        e = (-((f shr 27L.toInt()) or (f shl 5L.toInt())))
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = (((b + e) and 0xffffffffL) or ((((d + c) + e) xor f) shl 32L.toInt()))
    }
    private fun math_cc2fb4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = ((((b shr 29L.toInt()) or (b shl 3L.toInt())) + (x shr 32L.toInt())) + ((c shr 17L.toInt()) or (c shl 15L.toInt())))
        e = (c xor b)
        f = (d + e)
        b = (f xor b)
        c = (f xor c)
        d = ((c + e) xor d)
        e = (d * (-0x5a3c97c4L))
        f = table[((b and 255L) + 8L).toInt()]
        g = (f * (-0x5a3c97c2L))
        h = ((((b + e) - (c * 2L)) + g) and 0xffffffffL)
        b = ((((f + (d * 2L)) - b) + c) and 0xffffffffL)
        d = ((((h shr 29L.toInt()) or (h shl 3L.toInt())) + ((b shr 2L.toInt()) or (b shl 30L.toInt()))) xor ((d + ((f + d) * 0x5a3c97c2L)) + c))
        f = ((h + d) and 0xffffffffL)
        b = (((f + b) and 0xffffffffL) xor f)
        c = (((((e * 271407165L) + c) + (g * 990129773L)) + ((c - d) * 571076581L)) and 0xffffffffL)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = ((((((c shr 6L.toInt()) or (c shl 26L.toInt())) + ((b shr 13L.toInt()) or (b shl 19L.toInt()))) and 0xffffffffL) xor f) or ((c xor b) shl 32L.toInt()))
    }
    private fun math_cc3064(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x and 0xffffffffL)
        b = (((x shr 32L.toInt()) + y) and 0xffffffffL)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = (((((b shr 12L.toInt()) or (b shl 20L.toInt())) + y) + ((c shr 9L.toInt()) or (c shl 23L.toInt()))) and 0xffffffffL)
        e = ((d shr 27L.toInt()) or (d shl 5L.toInt()))
        f = table[(((((d * 2L) + b) - c) and 255L) + 471L).toInt()]
        g = (((((e - (d * 0x4d00c1d2L)) + (b * 3L)) - (c * 2L)) + f) and 0xffffffffL)
        h = (((b - (d * 0x4d00c1d3L)) - c) and 0xffffffffL)
        i = ((h shr 31L.toInt()) or (h shl 1L.toInt()))
        b = (((((d + f) - c) + e) + (b * 2L)) and 0xffffffffL)
        b = ((b shr 24L.toInt()) or (b shl 8L.toInt()))
        leaf0 = (a or (g shl 32L.toInt()))
        leaf1 = (((((h - d) - i) - b) and 0xffffffffL) or ((((d - ((g shr 15L.toInt()) or (g shl 17L.toInt()))) + b) + i) shl 32L.toInt()))
    }
    private fun math_cc30fc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = (((b shr 20L.toInt()) or (b shl 12L.toInt())) + (x shr 32L.toInt()))
        d = ((y shr 32L.toInt()) xor a)
        b = ((c + (d * 2L)) xor b)
        e = (table[(((c + d) and 255L) + 94L).toInt()] xor d)
        f = (b - e)
        e = (e xor ((((-c) - b) - d) * 0xd5aa72bL))
        g = (f - e)
        f = (-table[((f and 255L) + 287L).toInt()])
        h = (g xor (((c + b) + f) + d))
        b = (((((g + c) + b) + f) + d) xor e)
        c = (((-g) - b) + h)
        d = (b xor h)
        b = (((d + 0x49fd3915L) xor ((g + b) + 421459341L)) and 0xffffffffL)
        e = (((c - ((b shr 19L.toInt()) or (b shl 13L.toInt()))) + 0xe6e10a73L) and 0xffffffffL)
        c = (d xor table[(((c + 115L) and 255L) + 175L).toInt()])
        b = (b + c)
        leaf0 = (a or (e shl 32L.toInt()))
        leaf1 = ((b and 0xffffffffL) or (((b + 31220L) xor ((((e shr 18L.toInt()) or (e shl 14L.toInt())) - 19977L) xor c)) shl 32L.toInt()))
    }
    private fun math_cc31e8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x shr 32L.toInt())
        b = (y and 0xffffffffL)
        c = ((y shr 32L.toInt()) and 0xffffffffL)
        d = (a - ((((b shr 15L.toInt()) or (b shl 17L.toInt())) + 4138L) xor (c - 29269L)))
        a = (x - a)
        b = ((a + c) xor b)
        e = (a and 0xffffffffL)
        c = ((((((e shr 5L.toInt()) or (e shl 27L.toInt())) - 26683L) xor (d + 17547L)) and 0xffffffffL) xor c)
        e = ((d + (b * 2L)) - c)
        d = (d and 0xffffffffL)
        a = ((((d shr 25L.toInt()) or (d shl 7L.toInt())) + a) and 0xffffffffL)
        d = (e xor a)
        a = (c xor (((a shr 11L.toInt()) or (a shl 21L.toInt())) and 0xffffffffL))
        f = table[(((e + a) and 255L) + 101L).toInt()]
        b = ((((-((d - 20126L) xor (((a shr 8L.toInt()) or (a shl 24L.toInt())) - 21356L))) - c) + b) and 0xffffffffL)
        c = ((b shr 16L.toInt()) or (b shl 16L.toInt()))
        g = (((((-e) - a) - f) + d) + c)
        h = (g and 0xffffffffL)
        i = (d xor a)
        b = (b xor i)
        a = ((((table[((b and 255L) + 145L).toInt()] + e) + a) - c) and 0xffffffffL)
        c = (((a shr 5L.toInt()) or (a shl 27L.toInt())) and 0xffffffffL)
        d = ((((h + (f * 2L)) - (d * 2L)) + i) and 0xffffffffL)
        b = (b - ((((d shr 18L.toInt()) or (d shl 14L.toInt())) - 7887L) xor (g + 15737L)))
        leaf0 = ((h xor c) or ((a xor table[((b and 255L) + 89L).toInt()]) shl 32L.toInt()))
        leaf1 = (((b + (d xor h)) and 0xffffffffL) or ((d xor c) shl 32L.toInt()))
    }
    private fun math_cc3304(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = ((y + 0x43a8c11cL) xor (x shr 32L.toInt()))
        b = (x xor (y shr 32L.toInt()))
        c = (-table[((b and 255L) + 256L).toInt()])
        b = (((a * 2L) + b) and 0xffffffffL)
        d = ((b shr 1L.toInt()) or (b shl 31L.toInt()))
        leaf0 = ((x and 0xffffffffL) or ((((a - y) + c) - d) shl 32L.toInt()))
        leaf1 = ((table[(((((b * 2L) - y) + c) and 255L) + 504L).toInt()] xor b) or ((((b * 2L) - a) + d) shl 32L.toInt()))
    }
    private fun math_cc336c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (-(x shr 32L.toInt()))
        d = (((b + c) - y) and 0xffffffffL)
        e = ((b + y) xor ((d shr 29L.toInt()) or (d shl 3L.toInt())))
        f = (b * 7L)
        g = (y * 2L)
        h = (((((e * 3L) + (b * 5L)) + c) + y) and 0xffffffffL)
        i = ((h shr 11L.toInt()) or (h shl 21L.toInt()))
        c = (((((((f + g) + (e * 4L)) - i) + c) + 0x6ade67cL) xor (((((e * 5L) + f) + c) + g) + i)) and 0xffffffffL)
        leaf0 = (a or ((((h * 2L) - d) + 0x6ade67cL) shl 32L.toInt()))
        leaf1 = (c or (((((c + (h * 2L)) - d) + 0x6ade67cL) xor ((((b * (-2L)) - y) - (e * 2L)) - i)) shl 32L.toInt()))
    }
    private fun math_cc33dc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x xor (y shr 32L.toInt()))
        b = ((table[((a and 255L) + 188L).toInt()] + y) and 0xffffffffL)
        c = (x shr 32L.toInt())
        d = (((((b shr 7L.toInt()) or (b shl 25L.toInt())) + c) - y) and 0xffffffffL)
        a = ((a - table[(((c - y) and 255L) + 17L).toInt()]) and 0xffffffffL)
        b = (b xor ((a shr 29L.toInt()) or (a shl 3L.toInt())))
        c = (d xor table[((b and 255L) + 359L).toInt()])
        d = ((d shr 23L.toInt()) or (d shl 9L.toInt()))
        leaf0 = ((x and 0xffffffffL) or (c shl 32L.toInt()))
        leaf1 = (((((b - a) - d) - 329327389L) and 0xffffffffL) or (((a + c) + d) shl 32L.toInt()))
    }
    private fun math_cc34d0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        var i = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (y and 0xffffffffL)
        c = (((c shr 17L.toInt()) or (c shl 15L.toInt())) + (x shr 32L.toInt()))
        d = table[((c and 255L) + 506L).toInt()]
        e = ((((b shr 13L.toInt()) or (b shl 19L.toInt())) + y) and 0xffffffffL)
        c = ((c xor ((e shr 10L.toInt()) or (e shl 22L.toInt()))) and 0xffffffffL)
        e = (((c + (e * 0x70ae7845L)) + ((b - d) * 0x60a43536L)) and 0xffffffffL)
        f = ((e shr 13L.toInt()) or (e shl 19L.toInt()))
        g = (((c - e) * 270935411L) and 0xffffffffL)
        c = (-((c shr 1L.toInt()) or (c shl 31L.toInt())))
        h = (-((g shr 5L.toInt()) or (g shl 27L.toInt())))
        g = ((((((g - d) + b) + h) + c) - 0x7a20b302L) and 0xffffffffL)
        i = ((g shr 19L.toInt()) or (g shl 13L.toInt()))
        e = (((((((g - d) + b) + i) + h) + f) + c) xor e)
        b = (((((f + b) - d) + c) + h) + i)
        c = (b xor g)
        d = ((e + c) and 0xffffffffL)
        b = (e xor b)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = (((b + c) and 0xffffffffL) or ((((b * 2L) + c) + ((d shr 18L.toInt()) or (d shl 14L.toInt()))) shl 32L.toInt()))
    }
    private fun math_cc3590(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (((x shr 32L.toInt()) - y) and 0xffffffffL)
        c = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        d = (c xor (y and 0xffffffffL))
        e = ((((b shr 30L.toInt()) or (b shl 2L.toInt())) + 4462L) xor (d - 29986L))
        f = (d * (-0x75b48ad0L))
        d = (((((e + f) + c) + b) and 0xffffffffL) xor d)
        c = ((((e + f) + d) + c) + b)
        b = (((c + 9137L) xor (f + b)) xor (((d shr 13L.toInt()) or (d shl 19L.toInt())) - 8556L))
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((c + d) and 0xffffffffL) or (((c - b) + 0xabb69b66L) shl 32L.toInt()))
    }
    private fun mix_cbc2e4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = y
        c = ((x shr 32L.toInt()) xor b)
        d = ((y shr 32L.toInt()) xor a)
        b = ((d + 0x650e09ccL) xor b)
        e = (c xor table[((b and 255L) + 152L).toInt()])
        c = (d xor (b + c))
        d = ((c - e) and 0xffffffffL)
        b = ((b - (c * 0x4267b287L)) and 0xffffffffL)
        c = (e xor (((d shr 21L.toInt()) or (d shl 11L.toInt())) + ((b shr 24L.toInt()) or (b shl 8L.toInt()))))
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = (((b + d) and 0xffffffffL) or ((d - (c * 0x59a9a185L)) shl 32L.toInt()))
    }
    private fun mix_cbd25c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x shr 32L.toInt())
        b = (x - a)
        c = (y and 0xffffffffL)
        d = (y shr 32L.toInt())
        leaf0 = ((b and 0xffffffffL) or ((a xor ((c shr 15L.toInt()) or (c shl 17L.toInt()))) shl 32L.toInt()))
        leaf1 = ((((y - b) - d) and 0xffffffffL) or (((b + d) + 0xbe90e19L) shl 32L.toInt()))
    }
    private fun mix_cbd28c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (x shr 32L.toInt())
        d = ((b + c) + ((b - y) * 0x49a3f9d0L))
        e = ((((b * 2L) - c) - (y * 2L)) and 0xffffffffL)
        b = ((((y - ((((e shr 2L.toInt()) or (e shl 30L.toInt())) - 3756L) xor (d + 3901L))) - b) and 0xffffffffL) or ((((y * 0x49a3f9ceL) - (b * 0x49a3f9cfL)) - (c * 2L)) shl 32L.toInt()))
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = b
    }
    private fun mix_cbd8a8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = ((y + 278999759L) xor (x shr 32L.toInt()))
        c = ((y shr 32L.toInt()) xor a)
        d = (c xor b)
        b = (b xor ((c + y) + d))
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((((b + c) + y) + d) and 0xffffffffL) or ((d - b) shl 32L.toInt()))
    }
    private fun mix_cbda60(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (y and 0xffffffffL)
        c = (((x shr 32L.toInt()) xor ((b shr 18L.toInt()) or (b shl 14L.toInt()))) and 0xffffffffL)
        d = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        b = ((((c - 19832L) and 0xffffffffL) xor b) xor ((((d shr 26L.toInt()) or (d shl 6L.toInt())) - 7061L) and 0xffffffffL))
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = (b or ((((b - 6100L) xor (((c shr 16L.toInt()) or (c shl 16L.toInt())) - 7259L)) + d) shl 32L.toInt()))
    }
    private fun mix_cbe3c4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (y shr 32L.toInt())
        c = (x shr 32L.toInt())
        d = ((((y + c) - b) - 345624790L) and 0xffffffffL)
        e = ((d shr 4L.toInt()) or (d shl 28L.toInt()))
        b = ((((-e) - c) * 0x81a2145L) xor ((b - c) + 619238523L))
        d = ((((((e * 2L) + (c * 2L)) + (d xor b)) - b) + 0x6ef401eL) and 0xffffffffL)
        f = ((((d shr 7L.toInt()) or (d shl 25L.toInt())) + 376L) xor (((b - e) - c) + 0x67b28816L))
        b = (((f - d) + b) + 0x6ef401eL)
        leaf0 = (a or ((d - table[(((b + 38L) and 255L) + 105L).toInt()]) shl 32L.toInt()))
        leaf1 = (((b - 815507162L) and 0xffffffffL) or ((((((f - d) + e) + c) + 0x6ea16cb1L) xor a) shl 32L.toInt()))
    }
    private fun mix_cbe484(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x and 0xffffffffL)
        b = (((y + (x shr 32L.toInt())) - 760974399L) and 0xffffffffL)
        c = ((y shr 32L.toInt()) xor a)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((y - c) and 0xffffffffL) or ((c - ((b shr 18L.toInt()) or (b shl 14L.toInt()))) shl 32L.toInt()))
    }
    private fun mix_cbe4bc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (((b shr 19L.toInt()) or (b shl 13L.toInt())) + y)
        d = (-(x shr 32L.toInt()))
        leaf0 = (a or ((c + b) shl 32L.toInt()))
        leaf1 = (((c xor (d - y)) and 0xffffffffL) or ((((((-y) - b) - c) + d) - 0x6f19bc44L) shl 32L.toInt()))
    }
    private fun mix_cbefa8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = (b - y)
        d = ((y shr 32L.toInt()) xor a)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = (((d + b) and 0xffffffffL) or ((table[((c and 255L) + 393L).toInt()] xor d) shl 32L.toInt()))
    }
    private fun mix_cbf548(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x shr 32L.toInt())
        b = (((a + 0x67e17e93L) xor x) and 0xffffffffL)
        a = ((a xor y) and 0xffffffffL)
        c = (y shr 32L.toInt())
        d = (((((y shr 39L.toInt()) and 33554431L) or (c shl 25L.toInt())) + y) and 0xffffffffL)
        c = ((((((a shr 26L.toInt()) or (a shl 6L.toInt())) + c) + ((b shr 19L.toInt()) or (b shl 13L.toInt()))) + b) - a)
        leaf0 = (((b - a) and 0xffffffffL) or ((a - ((d shr 23L.toInt()) or (d shl 9L.toInt()))) shl 32L.toInt()))
        leaf1 = (((c + d) and 0xffffffffL) or (c shl 32L.toInt()))
    }
    private fun mix_cbf6e8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x shr 32L.toInt())
        b = ((a + x) and 0xffffffffL)
        a = (a + y)
        c = (y shr 32L.toInt())
        leaf0 = (b or (a shl 32L.toInt()))
        leaf1 = (((table[((c and 255L) + 206L).toInt()] + y) and 0xffffffffL) or ((c - ((((b shr 18L.toInt()) or (b shl 14L.toInt())) - 15432L) xor (a + 6137L))) shl 32L.toInt()))
    }
    private fun mix_cbf8d0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (x shr 32L.toInt())
        c = ((y shr 32L.toInt()) + b)
        d = (table[((c and 255L) + 299L).toInt()] xor y)
        b = (table[((d and 255L) + 167L).toInt()] xor b)
        c = (c - b)
        d = (d xor table[((c and 255L) + 233L).toInt()])
        e = ((c + d) and 0xffffffffL)
        b = (d xor b)
        d = (((e shr 2L.toInt()) or (e shl 30L.toInt())) + b)
        b = ((c - b) xor table[((d and 255L) + 47L).toInt()])
        c = (e xor b)
        d = ((d - (c * 0x7c7cdf0aL)) and 0xffffffffL)
        b = (((((d shr 17L.toInt()) or (d shl 15L.toInt())) + 13720L) xor (c + 31021L)) + b)
        leaf0 = (a or (((b + d) + c) shl 32L.toInt()))
        leaf1 = (((b + c) and 0xffffffffL) or ((b xor a) shl 32L.toInt()))
    }
    private fun mix_cbf990(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (x shr 32L.toInt())
        d = table[((b and 255L) + 388L).toInt()]
        b = ((b + c) + d)
        e = table[((b and 255L) + 413L).toInt()]
        d = (d + y)
        f = (d and 0xffffffffL)
        c = ((((f shr 26L.toInt()) or (f shl 6L.toInt())) - 23059L) xor ((c - y) xor (b + 26083L)))
        leaf0 = (a or (((((-b) - f) - e) + 0x6394a376L) shl 32L.toInt()))
        leaf1 = ((((d + e) xor ((b + c) + 0x9c6b5c8aL)) and 0xffffffffL) or (((c - f) - e) shl 32L.toInt()))
    }
    private fun mix_cbff20(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = ((-y) - b)
        d = ((x shr 32L.toInt()) xor y)
        b = (b xor d)
        d = ((d - c) xor table[((b and 255L) + 107L).toInt()])
        e = ((c + d) and 0xffffffffL)
        b = (b - table[((c and 255L) + 494L).toInt()])
        c = (d - table[(b and 255L).toInt()])
        d = (c and 0xffffffffL)
        d = (e xor ((d shr 13L.toInt()) or (d shl 19L.toInt())))
        b = (b xor ((e shr 9L.toInt()) or (e shl 23L.toInt())))
        c = ((c - table[((b and 255L) + 360L).toInt()]) and 0xffffffffL)
        e = (d xor c)
        f = ((((((-c) - b) - d) * 767472894L) and 0xffffffffL) xor c)
        g = table[((f and 255L) + 434L).toInt()]
        b = ((((c + b) + d) and 0xffffffffL) xor table[((e and 255L) + 477L).toInt()])
        c = (f xor (((b shr 28L.toInt()) or (b shl 4L.toInt())) and 0xffffffffL))
        leaf0 = (a or ((e - g) shl 32L.toInt()))
        leaf1 = (c or ((((b - c) - e) + g) shl 32L.toInt()))
    }
    private fun mix_cc0474(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = ((y xor (x shr 32L.toInt())) and 0xffffffffL)
        c = ((y shr 32L.toInt()) xor a)
        d = (y - c)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = ((d and 0xffffffffL) or ((((d + 14629L) xor (((b shr 7L.toInt()) or (b shl 25L.toInt())) + 1742L)) xor c) shl 32L.toInt()))
    }
    private fun mix_cc06c0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x shr 32L.toInt())
        b = (x - table[((a and 255L) + 477L).toInt()])
        c = (y shr 32L.toInt())
        leaf0 = ((b and 0xffffffffL) or (((c + y) xor a) shl 32L.toInt()))
        leaf1 = (((table[((c and 255L) + 97L).toInt()] + y) and 0xffffffffL) or ((b + c) shl 32L.toInt()))
    }
    private fun mix_cc09b4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (table[((b and 255L) + 96L).toInt()] xor y)
        d = (table[((y and 255L) + 439L).toInt()] xor (x shr 32L.toInt()))
        e = (table[((c and 255L) + 457L).toInt()] xor d)
        c = (((e + c) - b) + d)
        b = ((e * (-0x6e37e9edL)) xor (b - d))
        d = (((c - e) xor (b * (-0xb46cd0dL))) and 0xffffffffL)
        e = (-((d shr 9L.toInt()) or (d shl 23L.toInt())))
        b = (b xor c)
        d = (d + b)
        f = (((c + e) - table[((d and 255L) + 406L).toInt()]) and 0xffffffffL)
        b = ((c + b) + e)
        leaf0 = (a or (f shl 32L.toInt()))
        leaf1 = ((((b + 0x42c8cf2dL) xor d) and 0xffffffffL) or (((b - ((f shr 30L.toInt()) or (f shl 2L.toInt()))) + 0x42c8cf2dL) shl 32L.toInt()))
    }
    private fun mix_cc0cc0(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = (y shr 32L.toInt())
        c = (y and 0xffffffffL)
        d = (((((y shr 51L.toInt()) and 8191L) or (b shl 13L.toInt())) + ((c shr 14L.toInt()) or (c shl 18L.toInt()))) xor a)
        a = (x - (((x shr 36L.toInt()) and 0xfffffffL) or (a shl 28L.toInt())))
        e = (a xor b)
        f = ((((d - a) * 0x618aea69L) + e) and 0xffffffffL)
        b = (((a + b) xor c) + e)
        c = ((b + 36106196L) and 0xffffffffL)
        e = ((((f shr 12L.toInt()) or (f shl 20L.toInt())) + ((c shr 31L.toInt()) or (c shl 1L.toInt()))) and 0xffffffffL)
        a = ((a - d) and 0xffffffffL)
        g = (e xor a)
        b = (b + d)
        d = (b xor e)
        c = ((f + c) and 0xffffffffL)
        a = (f xor ((((b xor a) + 29250L) xor (d + 2395L)) and 0xffffffffL))
        leaf0 = (g or ((d - ((c shr 6L.toInt()) or (c shl 26L.toInt()))) shl 32L.toInt()))
        leaf1 = ((a xor c) or ((a xor ((g shr 31L.toInt()) or (g shl 1L.toInt()))) shl 32L.toInt()))
    }
    private fun mix_cc0d5c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x shr 32L.toInt())
        b = (y shr 32L.toInt())
        c = (y - (((y shr 39L.toInt()) and 33554431L) or (b shl 25L.toInt())))
        d = (c and 0xffffffffL)
        e = (a xor x)
        a = (a + y)
        b = (b xor (e + 0x7b5d37d5L))
        f = ((a - ((d shr 31L.toInt()) or (d shl 1L.toInt()))) and 0xffffffffL)
        c = (table[(((c - b) and 255L) + 299L).toInt()] xor f)
        a = ((e - table[((a and 255L) + 443L).toInt()]) and 0xffffffffL)
        e = ((((a shr 22L.toInt()) or (a shl 10L.toInt())) + ((f shr 12L.toInt()) or (f shl 20L.toInt()))) xor b)
        leaf0 = (((a - (f * 0x50278c2aL)) and 0xffffffffL) or (c shl 32L.toInt()))
        leaf1 = (((((((f * 0x50278c2aL) - e) - a) + d) - b) and 0xffffffffL) or ((((e - (f * 0x50278c2aL)) + a) + c) shl 32L.toInt()))
    }
    private fun mix_cc0df4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x - y)
        b = (x shr 32L.toInt())
        c = table[((y and 255L) + 85L).toInt()]
        d = (((a - (b * 2L)) + c) and 0xffffffffL)
        e = (y shr 32L.toInt())
        f = (e xor y)
        a = ((a - b) + e)
        leaf0 = (d or (((b - c) + table[((f and 255L) + 367L).toInt()]) shl 32L.toInt()))
        leaf1 = (((a + f) and 0xffffffffL) or ((a xor d) shl 32L.toInt()))
    }
    private fun mix_cc0e54(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (y shr 32L.toInt())
        b = ((x shr 32L.toInt()) and 0xffffffffL)
        c = (((y * (-969387324L)) and 0xffffffffL) xor b)
        d = ((c + ((y + a) * 945028798L)) - 43427200L)
        e = (y + a)
        b = (-table[((b and 255L) + 157L).toInt()])
        a = ((((a + x) + b) + 0x8e293cf5L) and 0xffffffffL)
        f = (((e + 0x8a2a9ac0L) xor ((a shr 31L.toInt()) or (a shl 1L.toInt()))) and 0xffffffffL)
        g = (d xor ((f shr 4L.toInt()) or (f shl 28L.toInt())))
        b = (((((c shr 31L.toInt()) or (c shl 1L.toInt())) - 10034L) xor ((e + 0x8a2a809aL) xor (x + b))) and 0xffffffffL)
        c = (b xor d)
        b = ((b shr 29L.toInt()) or (b shl 3L.toInt()))
        d = (f xor ((((-a) - b) * 0x69557badL) and 0xffffffffL))
        e = (-((d shr 6L.toInt()) or (d shl 26L.toInt())))
        a = ((a + b) xor table[((c and 255L) + 203L).toInt()])
        leaf0 = ((((g * 0x4e5c1a7fL) + c) and 0xffffffffL) or ((g + e) shl 32L.toInt()))
        leaf1 = (((d + a) and 0xffffffffL) or (((((g * 0x4e5c1a80L) + e) + a) + c) shl 32L.toInt()))
    }
    private fun mix_cc0f34(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (a xor (y shr 32L.toInt()))
        c = (table[((b and 255L) + 290L).toInt()] + y)
        d = (table[((y and 255L) + 417L).toInt()] xor (x shr 32L.toInt()))
        e = (c xor d)
        c = (((c - d) - b) and 0xffffffffL)
        f = (e - ((c shr 31L.toInt()) or (c shl 1L.toInt())))
        b = (e xor (d + b))
        leaf0 = (a or (((f + b) + table[((f and 255L) + 228L).toInt()]) shl 32L.toInt()))
        leaf1 = (((-f) and 0xffffffffL) or (((((-c) - (f * 2L)) + b) + 85603006L) shl 32L.toInt()))
    }
    private fun mix_cc0fc8(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = ((x shr 32L.toInt()) and 0xffffffffL)
        b = ((x - table[((a and 255L) + 428L).toInt()]) and 0xffffffffL)
        c = (y shr 32L.toInt())
        a = (((c + y) and 0xffffffffL) xor a)
        d = (b xor a)
        e = (b xor c)
        leaf0 = (d or (((((y - b) + a) - c) + e) shl 32L.toInt()))
        leaf1 = (((((y - b) - c) - e) and 0xffffffffL) or ((e - d) shl 32L.toInt()))
    }
    private fun mix_cc1010(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x shr 32L.toInt())
        b = (y and 0xffffffffL)
        b = ((a - ((b shr 2L.toInt()) or (b shl 30L.toInt()))) and 0xffffffffL)
        c = (((((b shr 10L.toInt()) or (b shl 22L.toInt())) + a) + x) and 0xffffffffL)
        d = (y shr 32L.toInt())
        e = (table[((d and 255L) + 270L).toInt()] + y)
        f = (e and 0xffffffffL)
        a = ((a + x) and 0xffffffffL)
        a = (d - ((a shr 24L.toInt()) or (a shl 8L.toInt())))
        d = (a and 0xffffffffL)
        leaf0 = (c or ((b xor (((f shr 8L.toInt()) or (f shl 24L.toInt())) + ((d shr 5L.toInt()) or (d shl 27L.toInt())))) shl 32L.toInt()))
        leaf1 = (((e - table[((a and 255L) + 346L).toInt()]) and 0xffffffffL) or ((d xor c) shl 32L.toInt()))
    }
    private fun mix_cc1080(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        a = (y shr 32L.toInt())
        b = (a + y)
        c = (b and 0xffffffffL)
        d = (x shr 32L.toInt())
        e = (c xor d)
        f = table[((e and 255L) + 409L).toInt()]
        d = (((y - 17428L) xor x) xor ((((x shr 51L.toInt()) and 8191L) or (d shl 13L.toInt())) + 5053L))
        c = ((c shr 28L.toInt()) or (c shl 4L.toInt()))
        g = (-table[((((e + a) + d) and 255L) + 12L).toInt()])
        h = ((((y + g) + f) + ((f + d) * 0x5d765e37L)) - e)
        b = ((b + g) and 0xffffffffL)
        b = ((((-((((b shr 2L.toInt()) or (b shl 30L.toInt())) - 13319L) xor ((b - h) - 17783L))) - c) + e) and 0xffffffffL)
        e = ((((((f + d) - e) + c) xor (h - 14814L)) xor (((b shr 27L.toInt()) or (b shl 5L.toInt())) + 24998L)) and 0xffffffffL)
        a = (((d + (((-f) - d) * 0x5d765e37L)) + a) + c)
        c = (a and 0xffffffffL)
        leaf0 = (e or ((b xor table[((h and 255L) + 210L).toInt()]) shl 32L.toInt()))
        leaf1 = (((h xor ((c shr 4L.toInt()) or (c shl 28L.toInt()))) and 0xffffffffL) or ((a - e) shl 32L.toInt()))
    }
    private fun mix_cc1160(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = ((x shr 32L.toInt()) + y)
        c = ((y shr 32L.toInt()) xor a)
        d = (((b + c) xor y) and 0xffffffffL)
        c = ((c - b) and 0xffffffffL)
        b = ((((d shr 16L.toInt()) or (d shl 16L.toInt())) + ((c shr 23L.toInt()) or (c shl 9L.toInt()))) xor b)
        e = ((c shr 8L.toInt()) or (c shl 24L.toInt()))
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((d + e) and 0xffffffffL) or ((((b + d) + e) xor c) shl 32L.toInt()))
    }
    private fun mix_cc1290(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x shr 32L.toInt())
        b = (y and 0xffffffffL)
        c = ((b shr 5L.toInt()) or (b shl 27L.toInt()))
        d = (y shr 32L.toInt())
        e = (((y shr 57L.toInt()) and 127L) or (d shl 7L.toInt()))
        f = (a xor x)
        g = ((((a - c) - e) * 909085917L) xor f)
        b = ((f + d) xor b)
        a = (b xor ((a - c) - e))
        c = (d - f)
        d = (c and 0xffffffffL)
        e = ((d shr 5L.toInt()) or (d shl 27L.toInt()))
        leaf0 = (((g + a) and 0xffffffffL) or (((((d - g) + a) + b) + e) shl 32L.toInt()))
        leaf1 = ((((((-d) - a) + b) + e) and 0xffffffffL) or (((c - (g * 2L)) - a) shl 32L.toInt()))
    }
    private fun mix_cc1384(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = (y and 0xffffffffL)
        c = (((b + 8085L) xor ((((c shr 14L.toInt()) or (c shl 18L.toInt())) - 2831L) xor (x shr 32L.toInt()))) and 0xffffffffL)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = ((((y - b) - c) and 0xffffffffL) or ((b xor ((c shr 30L.toInt()) or (c shl 2L.toInt()))) shl 32L.toInt()))
    }
    private fun mix_cc13c4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = ((x shr 32L.toInt()) - y)
        c = (b and 0xffffffffL)
        d = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        leaf0 = (a or (c shl 32L.toInt()))
        leaf1 = ((((((d shr 27L.toInt()) or (d shl 5L.toInt())) + ((c shr 19L.toInt()) or (c shl 13L.toInt()))) xor y) and 0xffffffffL) or ((b + d) shl 32L.toInt()))
    }
    private fun mix_cc165c(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = ((y shr 32L.toInt()) xor a)
        c = ((b * 0x4f8d62d4L) + y)
        d = (((x shr 32L.toInt()) xor y) and 0xffffffffL)
        e = (((c + 0x5e6ea6c4L) and 0xffffffffL) xor d)
        b = ((d * (-0x44ddcce4L)) xor b)
        c = (((b + 339250517L) xor c) and 0xffffffffL)
        d = (e xor c)
        b = (b - ((e shr 25L.toInt()) or (e shl 7L.toInt())))
        c = ((c - table[((b and 255L) + 174L).toInt()]) and 0xffffffffL)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = (c or (((b - ((d shr 5L.toInt()) or (d shl 27L.toInt()))) - ((c shr 15L.toInt()) or (c shl 17L.toInt()))) shl 32L.toInt()))
    }
    private fun mix_cc1acc(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = table[((y and 255L) + 253L).toInt()]
        c = (x shr 32L.toInt())
        d = ((y shr 32L.toInt()) xor a)
        leaf0 = (a or ((b + c) shl 32L.toInt()))
        leaf1 = (((y - (d * 0x492c0926L)) and 0xffffffffL) or (((d - b) - c) shl 32L.toInt()))
    }
    private fun mix_cc1c40(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (table[((y and 255L) + 285L).toInt()] + (x shr 32L.toInt()))
        c = ((y shr 32L.toInt()) xor a)
        d = ((b - c) - y)
        b = (b and 0xffffffffL)
        b = (((b shr 24L.toInt()) or (b shl 8L.toInt())) + c)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = ((((c + y) - table[((b and 255L) + 306L).toInt()]) and 0xffffffffL) or ((b - table[((d and 255L) + 314L).toInt()]) shl 32L.toInt()))
    }
    private fun mix_cc1fa4(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        a = (x and 0xffffffffL)
        b = (table[((y and 255L) + 218L).toInt()] xor (x shr 32L.toInt()))
        c = (b + ((y shr 32L.toInt()) xor a))
        d = (c + y)
        e = table[((c and 255L) + 366L).toInt()]
        f = (d xor e)
        d = (d xor b)
        g = ((d - c) + f)
        b = (e xor b)
        leaf0 = (a or ((g + b) shl 32L.toInt()))
        leaf1 = (((g xor ((c - d) - b)) and 0xffffffffL) or ((f + 59758798L) shl 32L.toInt()))
    }
    private fun mix_cc2530(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        a = (x and 0xffffffffL)
        b = (a xor (y shr 32L.toInt()))
        c = (table[((b and 255L) + 490L).toInt()] + y)
        d = (c and 0xffffffffL)
        d = ((d shr 18L.toInt()) or (d shl 14L.toInt()))
        e = (x shr 32L.toInt())
        f = ((((b - e) + y) + 0xa8c3ece9L) and 0xffffffffL)
        leaf0 = (a or ((((d + e) - y) + 0x573c1317L) shl 32L.toInt()))
        leaf1 = (((c - ((f shr 26L.toInt()) or (f shl 6L.toInt()))) and 0xffffffffL) or ((f + (((b - f) + d) * 49389384L)) shl 32L.toInt()))
    }
    private fun mix_cc2b40(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        a = (x and 0xffffffffL)
        b = (y xor (x shr 32L.toInt()))
        c = (a xor (y shr 32L.toInt()))
        d = (y - table[((c and 255L) + 20L).toInt()])
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = ((d and 0xffffffffL) or ((c xor (d + b)) shl 32L.toInt()))
    }
    private fun mix_cc2d70(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        a = (x and 0xffffffffL)
        b = (y xor (x shr 32L.toInt()))
        c = ((y shr 32L.toInt()) xor a)
        leaf0 = (a or (b shl 32L.toInt()))
        leaf1 = (((y - table[((c and 255L) + 186L).toInt()]) and 0xffffffffL) or ((c xor b) shl 32L.toInt()))
    }
    private fun mix_cc3460(x:Long, y:Long) {
        var a = 0L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        a = (x and 0xffffffffL)
        b = (((y shr 32L.toInt()) and 0xffffffffL) xor a)
        c = (y and 0xffffffffL)
        d = (((x shr 32L.toInt()) and 0xffffffffL) xor c)
        e = (b xor d)
        b = ((b + 0x7ee7eb4eL) xor c)
        c = ((e + d) + b)
        d = (c and 0xffffffffL)
        leaf0 = (a or (d shl 32L.toInt()))
        leaf1 = ((((b - ((e shr 3L.toInt()) or (e shl 29L.toInt()))) - ((d shr 28L.toInt()) or (d shl 4L.toInt()))) and 0xffffffffL) or ((e xor table[((c and 255L) + 317L).toInt()]) shl 32L.toInt()))
    }
}
