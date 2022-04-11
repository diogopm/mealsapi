package pt.diogopm.dto

import kotlinx.serialization.Serializable
import pt.diogopm.data.LocalizedField
import pt.diogopm.domain.DietaryType
import pt.diogopm.domain.Ingredient
import pt.diogopm.domain.MealCategory
import pt.diogopm.domain.MealItem


// --- Create / Update ---

@Serializable
data class IngredientSpecDto(
    val name: LocalizedField,
    val isAllergen: Boolean,
    val id: String? = null
)


@Serializable
data class DietaryTypeSpecDto(
    val name: LocalizedField,
    val id: String? = null
)


@Serializable
data class MealCategorySpecDto( // Entrada, Prato Principal, Sobremesa
    val name: LocalizedField,
    val id: String? = null
)


@Serializable
data class MealItemSpecDto( // Um prato
    val title: LocalizedField,
    val description: LocalizedField,
    val categoryId: String,
    val dietaryTypeId: String,
    val ingredientIds: List<String>,
    val id: String? = null
)

// --- Response data ---

@Serializable
data class IngredientDto(
    val id: String,
    val name: String,
    val isAllergen: Boolean
)


@Serializable
data class DietaryTypeDto(
// Carne, Peixe, Vegetariano, Dieta
    val id: String,
    val name: String,
)


@Serializable
data class MealCategoryDto( // Entrada, Prato Principal, Sobremesa
    val id: String,
    val name: String
)

@Serializable
data class MealItemDto( // Um prato
    val id: String,
    val title: String,
    val description: String,
    val category: MealCategoryDto,
    val type: DietaryTypeDto,
    val ingredients: List<IngredientDto>
)

// --- Mappers ---

fun MealItem.toDto(lang: String) = MealItemDto(
    id,
    title[lang],
    description[lang],
    category.toDto(lang),
    type.toDto(lang),
    ingredients.map { it.toDto(lang) }
)

fun MealCategory.toDto(lang: String) = MealCategoryDto(
    id,
    name[lang]
)

fun DietaryType.toDto(lang: String) = DietaryTypeDto(
    id,
    name[lang]
)


fun Ingredient.toDto(lang: String) = IngredientDto(
    id,
    name[lang],
    isAllergen
)
