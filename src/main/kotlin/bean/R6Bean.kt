package cf.liyu.bean

data class R6Bean(
    val code: Int,
    val message: String,
    val error: String,
    val payload: PayLoadBean
)

data class PayLoadBean(
    val exists: Boolean,
    val user: UserBean,
    val preview: List<PreviewBean>,
    val stats: StatsBean
)

data class UserBean(
    val nickname: String,
    val aliases: List<AliasesBean>
)

data class AliasesBean(
    val nickname: String,
    val timestam: Long
)

data class PreviewBean(
    val name: String,
    val value: String
)

data class StatsBean(
    val seasonal: SeasonalBean,
    val general: GeneralBean
)

data class SeasonalBean(
    val ranked: RankedBean,
    val casual: CasualBean
)

data class RankedBean(
    val rank: Int,
    val mmr: Double,
    val max_mmr: Double,
    val kills: Int,
    val deaths: Int,
    val wins: Int,
    val losses: Int,
    val last_match_mmr_change: Double,
    val banned: Boolean
)

data class CasualBean(
    val mmr: Double,
    val kills: Int,
    val deaths: Int,
    val wins: Int,
    val losses: Int,
    val abandons: Int,
    val last_match_mmr_change: Double,
)

data class GeneralBean(
    var kills: Int? = null,
    var deaths: Int? = null,
    var wins: Int? = null,
    var losses: Int? = null,
    var revives: Int? = null,
    var melees: Int? = null,
    var penetrations: Int? = null,
    var assists: Int? = null,
    var bulletshit: Int? = null,
    var bulletsfired: Int? = null,
    var timeplayed: Int? = null,
    var headshots: Int? = null,
    var distancetravelled: Int? = null,
    var barricadedeployed: Int? = null,
    var reinforcementdeploy: Int? = null,
    var suicide: Int? = null,
    var dbno: Int? = null,
    var dbnoassists: Int? = null,
    var gadgetdestroy: Int? = null,
    var blindkills: Int? = null
)

