package pt.diogopm.data

import org.bson.codecs.pojo.annotations.BsonId
import java.time.Instant
import java.util.*

const val dietAny = 0b1000
const val dietFish = 0b0100
const val dietVegetarian = 0b010
const val dietVegan = 0b001

data class IngredientAlt(
    val name: LocalizedField,
    val dietaryType: Int,
    val isAllergen: Boolean
)

// ^ pra pensar


data class IngredientMongoDbModel(
    val name: LocalizedField,
    val isAllergen: Boolean,
    val createdBy: String = "",
    val createdAt: Instant = Instant.now(),
    @BsonId val id: String = UUID.randomUUID().toString()
)


data class DietaryTypeMongoDbModel( // Carne, Peixe, Vegetariano, Dieta
    val name: LocalizedField,
    val createdBy: String = "",
    val createdAt: Instant = Instant.now(),
    @BsonId val id: String = UUID.randomUUID().toString()
)


data class MealCategoryMongoDbModel( // Entrada, Prato Principal, Sobremesa
    val name: LocalizedField,
    val createdBy: String = "",
    val createdAt: Instant = Instant.now(),
    @BsonId val id: String = UUID.randomUUID().toString()
)


data class MealItemMongoDbModel( // Um prato
    val title: LocalizedField,
    val description: LocalizedField,
    val categoryId: String,
    val dietaryTypeId: String,
    val ingredientIds: List<String>,
    val createdBy: String = "",
    val createdAt: Instant = Instant.now(),
    @BsonId val id: String = UUID.randomUUID().toString()
)



