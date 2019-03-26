package main.kotlin.service.java
// ˅
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.model.IAttribute
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import java.util.*
import java.text.*
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AttributeCollector

// ˄

class AddSetter : IMyPluginActionDelegate {
    // ˅
    
    // ˄

    override fun collectSelectedElements(): List<INamedElement> {
        // ˅
        return AttributeCollector().collectFromSelectedElements()
        // ˄
    }

    override fun excludeSetElements(selectedElements: List<INamedElement>): List<INamedElement> {
        // ˅
        val targetAttributes = mutableListOf<INamedElement>()

        loop@ for (selectedElemnt in selectedElements) {
            for (operation in (selectedElemnt.owner as IClass).operations) {
                if (operation.name == "set".plus(selectedElemnt.name.capitalize())) {   // Capitalize the first letter of the attribute name
                    continue@loop
                }
            }

            targetAttributes.add(selectedElemnt)
        }

        return targetAttributes
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            val basicModelEditor = ModelEditorFactory.getBasicModelEditor()
            val setter = basicModelEditor.createOperation(
                    (it as IAttribute).owner as IClass,
                    "set".plus(it.name.capitalize()),    // Capitalize the first letter of the attribute name
                    "void")
            basicModelEditor.createParameter(setter, "value", it.type)
            println("Added setter :".plus(setter.getFullName(".").plus("()")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
