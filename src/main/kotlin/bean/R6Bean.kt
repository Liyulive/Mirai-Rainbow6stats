package cf.liyu.bean
import com.google.gson.annotations.SerializedName


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
    val general: GeneralBean,
    val ranked: StatsRankdBean,
    val operators: List<OperatorsStatsBean>,
    val weaponDetails: List<WeaponDetailsBean>
)

data class SeasonalBean(
    val ranked: RankedBean,
    val casual: CasualBean
)

data class RankedBean(
    @SerializedName("abandons")
    val abandons: Int,
    @SerializedName("banned")
    val banned: Boolean,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("kills")
    val kills: Int,
    @SerializedName("last_match_mmr_change")
    val lastMatchMmrChange: Int,
    @SerializedName("last_match_result")
    val lastMatchResult: Int,
    @SerializedName("last_match_skill_mean_change")
    val lastMatchSkillMeanChange: Double,
    @SerializedName("losses")
    val losses: Int,
    @SerializedName("max_mmr")
    val maxMmr: Double,
    @SerializedName("max_rank")
    val maxRank: Int,
    @SerializedName("mmr")
    val mmr: Double,
    @SerializedName("next_rank_mmr")
    val nextRankMmr: Int,
    @SerializedName("noMatchesPlayed")
    val noMatchesPlayed: Boolean,
    @SerializedName("previous_rank_mmr")
    val previousRankMmr: Int,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("season")
    val season: Int,
    @SerializedName("skill_stdev")
    val skillStdev: Double,
    @SerializedName("top_rank_position")
    val topRankPosition: Int,
    @SerializedName("update_time")
    val updateTime: Long,
    @SerializedName("wins")
    val wins: Int
)

data class CasualBean(
    @SerializedName("abandons")
    val abandons: Int,
    @SerializedName("banned")
    val banned: Boolean,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("kills")
    val kills: Int,
    @SerializedName("last_match_mmr_change")
    val lastMatchMmrChange: Int,
    @SerializedName("last_match_result")
    val lastMatchResult: Int,
    @SerializedName("last_match_skill_mean_change")
    val lastMatchSkillMeanChange: Double,
    @SerializedName("losses")
    val losses: Int,
    @SerializedName("mmr")
    val mmr: Double,
    @SerializedName("noMatchesPlayed")
    val noMatchesPlayed: Boolean,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("season")
    val season: Int,
    @SerializedName("update_time")
    val updateTime: Long,
    @SerializedName("wins")
    val wins: Int
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
    var barricadedeployed: Int? = null,
    var reinforcementdeploy: Int? = null,
    var suicide: Int? = null,
    var dbno: Int? = null,
    var dbnoassists: Int? = null,
    var gadgetdestroy: Int? = null,
    var blindkills: Int? = null
)

data class StatsRankdBean(
    val kills: Int,
    val deaths: Int,
    val wins: Int,
    val losses: Int,
    val matchesplayed: Int,
    val timeplayed: Int
)

data class OperatorsStatsBean(
    var id: String? = null,
    var kills: Int? = null,
    var deaths: Int? = null,
    var headshots: Int? = null,
    var meleekills: Int? = null,
    var dbno: Int? = null,
    var wins: Int? = null,
    var losses: Int? = null,
    var roundsplayed: Int? = null,
    var timeplayed: Int? = null,
    var totalxp: Int? = null
)

data class WeaponDetailsBean(

    var key: String? = null,
    var name: String? = null,
    var fired: Int? = null,
    var hits: Int? = null,
    var headshots: Int? = null,
    var kills: Int? = null,
    var deaths: Int? = null

)

data class HistorySeasonBean(
    val emea: EmeaBean,
    val ncsa: EmeaBean,
    val apac: EmeaBean
)

data class EmeaBean(

    val rank: Int,
    val mmr: Double,
    val skillStdev: Double,
    val maxRank: Int,
    val max_mmr: Double,
    val season: Int,
    val kills: Int,
    val deaths: Int,
    val wins: Int,
    val losses: Int,
    val abandons: Int,
    val lastMatchMmrChange: Int,
    val topRankPosition: Int,
    val lastMatchSkillMeanChange: Double,
    val lastMatchResult: Int,
    val nextRankMmr: Int,
    val previousRankMmr: Int,
    val updateTime: Int,
    val noMatchesPlayed: Boolean,
    val banned: Boolean

)
