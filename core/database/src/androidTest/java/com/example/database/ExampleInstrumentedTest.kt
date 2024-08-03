package com.example.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.database.dao.ExpensesDao
import com.example.database.entities.ExpensesDbo
import com.example.database.entities.ExpensesTypeDbo
import com.example.models.BudgetEntity
import com.example.models.ExpensesType
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.database.test", appContext.packageName)
//    }

    private lateinit var expensesDao: ExpensesDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        expensesDao = db.expensesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insert_record() {
        val record = expensesDao.insertRecord(
            ExpensesDbo.create(
                type = ExpensesTypeDbo.SPENDING,
                value = 420.0,
                date = Date()
            )
        )
        val recordId = record.blockingGet()
        val record2 = expensesDao.insertRecord(
            ExpensesDbo.create(
                type = ExpensesTypeDbo.SPENDING,
                value = 430.0,
                date = Date()
            )
        )
        val record2Id = record2.blockingGet()
        Assert.assertEquals(1, recordId)
        Assert.assertEquals(2, record2Id)

    }

    @Test
    @Throws(Exception::class)
    fun find_record() {
        val entity = ExpensesDbo.create(
            id = 1,
            type = ExpensesTypeDbo.SPENDING,
            value = 420.0,
            date = Date()
        )
        val record = expensesDao.insertRecord(entity)
        val recordId = record.blockingGet()

        val foundEntity = expensesDao.getRecordById(recordId).blockingGet()

        Assert.assertEquals(entity, foundEntity)

    }

    @Test
    @Throws(Exception::class)
    fun delete_record() {
        val record = expensesDao.insertRecord(
            ExpensesDbo.create(
                type = ExpensesTypeDbo.SPENDING,
                value = 420.0,
                date = Date()
            )
        )
        val recordId = record.blockingGet()
        val record2 = expensesDao.insertRecord(
            ExpensesDbo.create(
                type = ExpensesTypeDbo.SPENDING,
                value = 430.0,
                date = Date()
            )
        )
        val record2Id = record2.blockingGet()
        expensesDao.deleteRecord(expensesDao.getRecordById(recordId).blockingGet())

        val allRecords = expensesDao.getAllRecords()

        allRecords.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { items ->
                Assert.assertTrue(items.isNotEmpty())
                items.forEach {
                    Assert.assertTrue(items.contains(it))
                }
                Assert.assertEquals(1, items.size)
                Assert.assertEquals(record2Id, items[0].id)
            }

    }

    @Test
    @Throws(Exception::class)
    fun return_all_record_when_table_is_empty() {

        val allRecords = expensesDao.getAllRecords()

        val sd = allRecords
            .map { list ->
                list.map { item ->
                    BudgetEntity(
                        id = item.id,
                        type = ExpensesType.SPENDING,
                        value = item.value,
                        year = item.year,
                        month = item.month,
                        day = item.day,
                        time = item.time

                    )
                }
            }.blockingFirst()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { items ->
//                Assert.assertEquals(items, 1)
//            }
        assertEquals(sd, emptyList<BudgetEntity>())
    }

    @Test
    @Throws(Exception::class)
    fun testin() {

        val result: Flowable<List<Long>> = Flowable.just(emptyList())
        val qwe = result.map { it.map { it.toInt() } }.blockingFirst()
        assertEquals(emptyList<Int>(), qwe)

    }
}