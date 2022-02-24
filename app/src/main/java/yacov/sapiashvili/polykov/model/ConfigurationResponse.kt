package yacov.sapiashvili.polykov.model

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
    @SerializedName("1_degrees")
    val polygonDegreesToRotate: Int,

    @SerializedName("1_vertices")
    val polygonNumberOfVertices: Int,

    @SerializedName("2")
    val rectangleAnimationDuration: Int,

    val period: Int
)