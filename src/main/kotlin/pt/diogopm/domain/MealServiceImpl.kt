package pt.diogopm.domain

import kotlinx.coroutines.coroutineScope
import pt.diogopm.data.MealRepository

interface MealService {

    suspend fun getMeal(id: String): MealItem

    suspend fun getAllMeals(): List<MealItem>

    suspend fun insert(meals: List<MealItemSpec>)

    suspend fun insert(item: MealItemSpec): String?

    suspend fun insert(item: MealCategorySpec): String?

    suspend fun insert(item: DietaryTypeSpec): String?

    suspend fun insert(item: IngredientSpec): String?
}


class MealServiceImpl(val repository: MealRepository) : MealService {

    override suspend fun getMeal(id: String) = coroutineScope {
        return@coroutineScope repository.getMeal(id)
    }

    override suspend fun getAllMeals() = coroutineScope {
        return@coroutineScope repository.getAllMeals()
    }

    override suspend fun insert(meals: List<MealItemSpec>) = coroutineScope {
        repository.insert(meals)
        Unit
    }

    override suspend fun insert(item: MealItemSpec) = coroutineScope {
        return@coroutineScope repository.insert(item)
    }

    override suspend fun insert(item: MealCategorySpec) = coroutineScope {
        return@coroutineScope repository.insert(item)
    }

    override suspend fun insert(item: DietaryTypeSpec) = coroutineScope {
        return@coroutineScope repository.insert(item)
    }

    override suspend fun insert(item: IngredientSpec) = coroutineScope {
        return@coroutineScope repository.insert(item)
    }


}