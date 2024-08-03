package com.example.finance.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.data.repositories.ExpensesRepository
import com.example.data.usecases.DeleteRecordByIdUseCase
import com.example.data.usecases.GetAllRecordsUseCase
import com.example.data.usecases.GetTotalMoneyUseCase
import com.example.finance.adapter.models.BudgetUi
import com.example.models.BudgetEntity
import com.example.models.MoneyTotalEntity
import com.example.ui.BaseViewModel
import com.example.ui.livedata.ErrorHolder
import com.example.ui.livedata.StatefulLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository,
    private val deleteRecordByIdUseCase: DeleteRecordByIdUseCase,
    private val getAllRecordsUseCase: GetAllRecordsUseCase,
    private val getTotalMoneyUseCase: GetTotalMoneyUseCase
) : BaseViewModel() {

    private val moneyTotalLiveData = StatefulLiveData<MoneyTotalEntity>()

    fun observeSuccessMoneyTotalLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<MoneyTotalEntity>
    ) = moneyTotalLiveData.observeSuccessLiveData(lifecycleOwner, observer)

    fun observeErrorMoneyTotalLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<ErrorHolder>
    ) = moneyTotalLiveData.observeErrorLiveData(lifecycleOwner, observer)

    fun observeLoadingMoneyTotalLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<Boolean>
    ) = moneyTotalLiveData.observeLoadingLiveData(lifecycleOwner, observer)

    private val budgetListLiveData = StatefulLiveData<List<BudgetUi>>()

    fun observeSuccessBudgetListLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<List<BudgetUi>>
    ) = budgetListLiveData.observeSuccessLiveData(lifecycleOwner, observer)

    fun observeErrorBudgetListLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<ErrorHolder>
    ) = budgetListLiveData.observeErrorLiveData(lifecycleOwner, observer)

    fun observeLoadingBudgetListLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<Boolean>
    ) = budgetListLiveData.observeLoadingLiveData(lifecycleOwner, observer)

    init {

        getTotalMoneyUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { moneyTotalLiveData.setValueLoadingLiveData(true) }
            .subscribeBy(
                onNext = { moneyTotal ->
                    Timber.d(moneyTotal.toString())
                    moneyTotalLiveData.setValueLoadingLiveData(false)
                    moneyTotalLiveData.setValueSuccessLiveData(moneyTotal)
                },
                onComplete = {},
                onError = {
                    Log.d("FinanceViewModel error", "$it: ${it.message}")
                }
            ).addTo(disposables)

        getAllRecordsUseCase.invoke()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { budgetListLiveData.setValueLoadingLiveData(true) }
            .subscribeBy(
                onNext = { list ->
                    budgetListLiveData.setValueLoadingLiveData(false)
                    budgetListLiveData.setValueSuccessLiveData(
                        qwe3(list)
                    )
                    Log.d("FinanceViewModel success", list.toString())
                },
                onComplete = {},
                onError = {
                    budgetListLiveData.setValueLoadingLiveData(false)
                    budgetListLiveData.setValueErrorLiveData(
                        ErrorHolder(
                            error = it,
                            message = it.toString()
                        )
                    )
                    Log.d("FinanceViewModel error", "${it}: ${it.message}")
                }
            ).addTo(disposables)
    }


    fun deleteRecord(id: Long) {
        deleteRecordByIdUseCase.invoke(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe().addTo(disposables)
    }

//    private fun qwe(list: List<BudgetEntity>): List<BudgetUi> {
//        var compareDate: String? = null
//        val resultList = mutableListOf<BudgetUi>()
//
//        list.forEach { item ->
//            if (compareDate == null) {
//                compareDate = dateParser(item.day, item.month)
//                resultList.add(BudgetUi.Date(compareDate!!))
//                resultList.add(
//                    BudgetUi.Spending(
//                        id = item.id,
//                        value = item.value,
//                        time = item.time
//                    )
//                )
//            } else {
//                val date = dateParser(item.day, item.month)
//                if (compareDate!!.contains(date))
//                    resultList.add(
//                        BudgetUi.Spending(
//                            id = item.id,
//                            value = item.value,
//                            time = item.time
//                        )
//                    )
//                else {
//                    compareDate = dateParser(item.day, item.month)
//                    resultList.add(BudgetUi.Date(compareDate!!))
//                    resultList.add(
//                        BudgetUi.Spending(
//                            id = item.id,
//                            value = item.value,
//                            time = item.time
//                        )
//                    )
//                }
//
//            }
//        }
//        return resultList
//    }
//
//    private fun qwe2(list: List<BudgetEntity>): List<BudgetUi> {
//        var compareDate: String? = null
//        val resultList = mutableListOf<BudgetUi>()
//        if (list.isEmpty()) return resultList
//        list.forEach { item ->
//            if (compareDate == null) {
//                compareDate = dateParser(item.day, item.month)
//                resultList.add(
//                    BudgetUi.Spending(
//                        id = item.id,
//                        value = item.value,
//                        time = item.time
//                    )
//                )
//            } else {
//                val date = dateParser(item.day, item.month)
//                if (compareDate!!.contains(date))
//                    resultList.add(
//                        BudgetUi.Spending(
//                            id = item.id,
//                            value = item.value,
//                            time = item.time
//                        )
//                    )
//                else {
//                    resultList.add(BudgetUi.Date(compareDate!!))
//                    compareDate = dateParser(item.day, item.month)
//                    resultList.add(
//                        BudgetUi.Spending(
//                            id = item.id,
//                            value = item.value,
//                            time = item.time
//                        )
//                    )
//                }
//
//            }
//        }
//        resultList.add(BudgetUi.Date(compareDate!!))
//        return resultList
//    }

    private fun qwe3(list: List<BudgetEntity>): List<BudgetUi> {
        var compareDate: String? = null
        val resultList = mutableListOf<BudgetUi>()
        val containerList = mutableListOf<BudgetUiContainer>()
        if (list.isEmpty()) return resultList
        list.forEach { item ->
            if (compareDate == null) {
                compareDate = dateParser(item.day, item.month)

                containerList.add(
                    BudgetUiContainer(
                        date = compareDate!!,
                        list = mutableListOf()
                    )
                )
                containerList[containerList.lastIndex].list.add(item)
            } else {
                val date = dateParser(item.day, item.month)
                if (compareDate!!.contains(date))

                    containerList[containerList.lastIndex].list.add(item)
                else {
                    compareDate = dateParser(item.day, item.month)
                    containerList.add(
                        BudgetUiContainer(
                            date = compareDate!!,
                            list = mutableListOf()
                        )
                    )

                    containerList[containerList.lastIndex].list.add(item)

                }

            }
        }

        containerList.forEach { item ->

            if (item.list.size == 1) {
                resultList.add(
                    BudgetUi.Spending(
                        id = item.list.first().id,
                        value = item.list.first().value,
                        time = item.list.first().time,
                        cornersDirection = BudgetUi.CornersDirection.ALL,
                        type = item.list.first().type
                    )
                )

                resultList.add(
                    BudgetUi.Date(
                        date = item.date
                    )
                )
            }

            if (item.list.size == 2) {

                resultList.add(
                    BudgetUi.Spending(
                        id = item.list.first().id,
                        value = item.list.first().value,
                        time = item.list.first().time,
                        cornersDirection = BudgetUi.CornersDirection.BOTTOM,
                        type = item.list.first().type

                    )
                )

                resultList.add(
                    BudgetUi.Spending(
                        id = item.list.last().id,
                        value = item.list.last().value,
                        time = item.list.last().time,
                        cornersDirection = BudgetUi.CornersDirection.TOP,
                        type = item.list.last().type

                    )
                )

                resultList.add(
                    BudgetUi.Date(
                        date = item.date
                    )
                )
            }

            if (item.list.size >= 3) {

                resultList.add(
                    BudgetUi.Spending(
                        id = item.list.first().id,
                        value = item.list.first().value,
                        time = item.list.first().time,
                        cornersDirection = BudgetUi.CornersDirection.BOTTOM,
                        type = item.list.first().type

                    )
                )

                for (i in 1 until item.list.size - 1) {
                    resultList.add(
                        BudgetUi.Spending(
                            id = item.list[i].id,
                            value = item.list[i].value,
                            time = item.list[i].time,
                            cornersDirection = BudgetUi.CornersDirection.NOT,
                            type = item.list[i].type

                        )
                    )
                }

                resultList.add(
                    BudgetUi.Spending(
                        id = item.list.last().id,
                        value = item.list.last().value,
                        time = item.list.last().time,
                        cornersDirection = BudgetUi.CornersDirection.TOP,
                        type = item.list.last().type
                    )
                )

                resultList.add(
                    BudgetUi.Date(
                        date = item.date
                    )
                )
            }
        }
        return resultList
    }

    private fun dateParser(day: String, month: String): String {
        val date = SimpleDateFormat(
            "d MM",
            Locale("ru", "RU")
        ).parse(
            "$day $month"
        )
        val string = SimpleDateFormat(
            "d MMMM",
            Locale("ru", "RU")
        ).format(date)
        return string
    }

    private data class BudgetUiContainer(
        val date: String,
        val list: MutableList<BudgetEntity>
    )

}