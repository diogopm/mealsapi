package pt.diogopm.data

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import pt.diogopm.domain.*


val client = KMongo.createClient("mongodb://192.168.1.89:27017").coroutine
val database = client.getDatabase("meals")


class MealRepository(db: CoroutineDatabase = database) {

    private val mealItemsCollection = db.getCollection<MealItemMongoDbModel>("meal_items")
    private val mealCategoriesCollection = db.getCollection<MealCategoryMongoDbModel>("meal_categories")
    private val mealDietTypesCollection = db.getCollection<DietaryTypeMongoDbModel>("meal_diet_types")
    private val ingredientsCollection = db.getCollection<IngredientMongoDbModel>("ingredients")


    suspend fun getMeal(id: String): MealItem = coroutineScope {
        val mealItem = mealItemsCollection.findOneById(id) ?: throw IllegalArgumentException("Id not found")
        return@coroutineScope mealItem.toDomain()
    }

    suspend fun getAllMeals() = coroutineScope {
        val list = mealItemsCollection.find().toList()
        list.map { it.toDomain() }
    }

    private suspend fun getCategory(id: String) =
        mealCategoriesCollection.findOneById(id) ?: throw IllegalArgumentException("Unknown Category id $id")

    private suspend fun getDietaryType(id: String) =
        mealDietTypesCollection.findOneById(id) ?: throw IllegalArgumentException("Unknown Dietary Type id $id")

    private suspend fun getIngredient(id: String) =
        ingredientsCollection.findOneById(id) ?: throw IllegalArgumentException("Unknown Ingredient id $id")

    suspend fun insert(item: MealItemSpec) = coroutineScope {
        val dbModel = item.toDb()
        val result = mealItemsCollection.insertOne(dbModel)
        return@coroutineScope if (result.wasAcknowledged()) dbModel.id else null
    }


    suspend fun insert(item: MealCategorySpec) = coroutineScope {
        val dbModel = item.toDb()
        val result = mealCategoriesCollection.insertOne(dbModel)
        return@coroutineScope if (result.wasAcknowledged()) dbModel.id else null
    }

    suspend fun insert(meals: List<MealItemSpec>) {
        val dbModels = meals.map { it.toDb() }
        val insertMany = mealItemsCollection.insertMany(dbModels)

    }

    suspend fun insert(item: DietaryTypeSpec) = coroutineScope {
        val dbModel = item.toDb()
        val result = mealDietTypesCollection.insertOne(dbModel)
        return@coroutineScope if (result.wasAcknowledged()) dbModel.id else null
    }


    suspend fun insert(item: IngredientSpec) = coroutineScope {
        val dbModel = item.toDb()
        val result = ingredientsCollection.insertOne(dbModel)
        return@coroutineScope if (result.wasAcknowledged()) dbModel.id else null
    }

    suspend fun MealItemMongoDbModel.toDomain() = coroutineScope {
        val category = async { getCategory(categoryId) }
        val dietaryType = async { getDietaryType(dietaryTypeId) }
        val ingredients = async { ingredientIds.map { getIngredient(it) } }

        return@coroutineScope MealItem(
            id,
            title,
            description,
            category.await().toDomain(),
            dietaryType.await().toDomain(),
            ingredients.await().map { it.toDomain() }
        )
    }

    private fun MealItemSpec.toDb() = MealItemMongoDbModel(title, description, categoryId, dietaryTypeId, ingredientIds)

    private fun MealCategorySpec.toDb() = MealCategoryMongoDbModel(name)

    private fun DietaryTypeSpec.toDb() = DietaryTypeMongoDbModel(name)

    private fun IngredientSpec.toDb() = IngredientMongoDbModel(name, isAllergen)

    private fun MealCategoryMongoDbModel.toDomain() = MealCategory(id, name)

    private fun IngredientMongoDbModel.toDomain() = Ingredient(id, name, isAllergen)

    private fun DietaryTypeMongoDbModel.toDomain() = DietaryType(id, name)

}



