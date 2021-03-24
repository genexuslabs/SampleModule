package com.example.samplemodule

import android.content.Context
import android.content.Intent
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatTextView
import com.artech.base.controls.IGxControlRuntime
import com.artech.base.metadata.expressions.Expression
import com.artech.base.metadata.layout.LayoutItemDefinition
import com.artech.controls.IGxEdit
import com.artech.ui.Coordinator
import com.example.genexusmodule.R

@Suppress("ViewConstructor", "UNUSED_PARAMETER")
class BasicUserControl(
		context: Context,
		private val mCoordinator: Coordinator,
		definition: LayoutItemDefinition?
) : AppCompatTextView(context), IGxEdit, IGxControlRuntime {

	private var mName: String? = null
	private var tapCount = 0

	override fun callMethod(methodName: String, parameters: List<Expression.Value>): Expression.Value? {
		if (METHOD_SET_NAME == methodName) {
			val name = parameters[0].coerceToString()
			setName(name)
		}

		return null
	}

	private val mOnClickListener = OnClickListener {
		tapCount++
		runOnTapEvent()
	}

	private fun runOnTapEvent() {
		val actionDef = mCoordinator.getControlEventHandler(this, EVENT_ON_TAP)

		for (param in actionDef.eventParameters) {
			val paramName = param.valueDefinition.name
			mCoordinator.setValue(paramName, tapCount)
		}

		mCoordinator.runControlEvent(this, EVENT_ON_TAP)
	}

	private fun setName(name: String?) {
		mName = name
		text = context.getString(R.string.welcome_message, name)
	}

	override fun getGxValue(): String? {
		return mName
	}

	override fun setGxValue(value: String) {
		setName(value)
	}

	override fun getGxTag(): String? {
		return tag?.toString()
	}

	override fun setGxTag(tag: String) {
		setTag(tag)
	}

	override fun isEditable(): Boolean {
		return false
	}

	override fun getViewControl(): IGxEdit {
		return this
	}

	override fun getEditControl(): IGxEdit? {
		return null
	}

	override fun setValueFromIntent(data: Intent) {}

	companion object {
		const val NAME = "BasicUserControl"
		private const val METHOD_SET_NAME = "SetName"
		private const val EVENT_ON_TAP = "OnTap"
	}

	init {
		setOnClickListener(mOnClickListener)
	}
}