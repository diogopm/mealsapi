package pt.diogopm.domain

import kotlinx.serialization.Serializable
import pt.diogopm.data.LocalizedField

@Serializable
data class IngredientSpec(
    val name: LocalizedField,
    val isAllergen: Boolean,
    val user: String = "",
    val id: String? = null
)


@Serializable
data class DietaryTypeSpec(
    val name: LocalizedField,
    val user: String = "",
    val id: String? = null
)


@Serializable
data class MealCategorySpec( // Entrada, Prato Principal, Sobremesa
    val name: LocalizedField,
    val user: String = "",
    val id: String? = null
)


@Serializable
data class MealItemSpec( // Um prato
    val title: LocalizedField,
    val description: LocalizedField,
    val categoryId: String,
    val dietaryTypeId: String,
    val ingredientIds: List<String>,
    val user: String = "",
    val id: String? = null
)

data class Ingredient(
    val id: String,
    val name: LocalizedField,
    val isAllergen: Boolean,
)


data class DietaryType(
// Carne, Peixe, Vegetariano, Dieta
    val id: String,
    val name: LocalizedField,
)


data class MealCategory( // Entrada, Prato Principal, Sobremesa
    val id: String,
    val name: LocalizedField
)

data class MealItem( // Um prato
    val id: String,
    val title: LocalizedField,
    val description: LocalizedField,
    val category: MealCategory,
    val type: DietaryType,
    val ingredients: List<Ingredient>
)
