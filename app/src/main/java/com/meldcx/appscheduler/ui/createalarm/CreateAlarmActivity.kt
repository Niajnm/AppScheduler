package com.meldcx.appscheduler.ui.createalarm

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.meldcx.appscheduler.data.Alarm
import com.meldcx.appscheduler.di.MainActivityComponent
import com.meldcx.appscheduler.ui.selectapp.AppListActivity
import com.meldcx.appscheduler.ui.base.BaseActivity
import com.meldcx.appscheduler.utils.Constant.Companion.APP_ID
import com.meldcx.appscheduler.utils.Constant.Companion.APP_NAME
import com.meldcx.appscheduler.utils.TimePickerUtil
import dev.ronnie.imageloaderdagger2.R
import dev.ronnie.imageloaderdagger2.databinding.ActivityCreatealarmBinding
import kotlinx.android.synthetic.main.activity_createalarm.*
import java.util.*
import javax.inject.Inject

class CreateAlarmActivity : BaseActivity<ActivityCreatealarmBinding>() {
    @Inject
    lateinit var createTaskViewModel: CreateAlarmViewModel
    private var mAppId: String = String()

    override fun performDependencyInjection(activityComponent: MainActivityComponent) {
        activityComponent.inject(this)
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_createalarm
    }

    override fun initComponents() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
        }

        binding.apply {
            viewModel = createTaskViewModel
            activity = this@CreateAlarmActivity
        }
    }

    fun createAlarm() {
        scheduleAlarm()
    }

    fun openAppList() {
        startForResult.launch(Intent(this, AppListActivity::class.java))
    }

    private fun scheduleAlarm() {
        val alarmId = Random().nextInt(Int.MAX_VALUE)
        val alarm = Alarm(alarmId, TimePickerUtil.getTimePickerHour(fragment_createalarm_timePicker), TimePickerUtil.getTimePickerMinute(fragment_createalarm_timePicker), text_app_package_name.text.toString(), mAppId, System.currentTimeMillis(), true, check_recurring.isChecked, check_mon.isChecked, check_tue.isChecked, check_wed.isChecked, check_thu.isChecked, check_fri.isChecked, check_sat.isChecked, check_sun.isChecked)
        if (createTaskViewModel.isTaskValid(alarm)) {
            createTaskViewModel.insert(alarm)
            alarm.schedule(this)
            finish()
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                binding.textAppPackageName.text = intent?.getStringExtra(APP_NAME)
                mAppId = intent?.getStringExtra(APP_ID)!!
            }
        }
}