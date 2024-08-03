package com.example.financecontrol.navigation.finance

import com.example.add.AddDialogFragment
import com.example.finance.FinanceFragmentContract
import com.example.financecontrol.navigation.NavigateRouter
import com.example.money.ChangeMoneyDialogFragment
import javax.inject.Inject

class FinanceFragmentContractImpl @Inject constructor(
    private val navigateRouter: NavigateRouter
) : FinanceFragmentContract {
    override fun launchAddDialogFragment() {
        val dialog = AddDialogFragment()
        dialog.show(navigateRouter.requireActivity().supportFragmentManager, "Add")
    }

    override fun launchChangeMoneyDialogFragment() {
        val dialog = ChangeMoneyDialogFragment()
        dialog.show(navigateRouter.requireActivity().supportFragmentManager, "ChangeMoney")
    }
}