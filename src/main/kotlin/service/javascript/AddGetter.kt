package main.kotlin.service.javascript
// ˅
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.model.IAttribute
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import java.util.*
import java.text.*
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AttributeCollector
import main.kotlin.util.collector.ClassCollector

// ˄

class AddGetter : IMyPluginActionDelegate {
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

        loop@ for (selectedElement in selectedElements) {
            for (operation in (selectedElement.owner as IClass).operations) {
                if (operation.name == selectedElement.name
                        && operation.hasStereotype("get")) {
                    continue@loop
                }
            }

            targetAttributes.add(selectedElement)
        }

        return targetAttributes
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            val basicModelEditor = ModelEditorFactory.getBasicModelEditor()
            val getter = basicModelEditor.createOperation((it as IAttribute).owner as IClass, it.name, "")
            getter.addStereotype("get")
            println("Added getter :".plus(getter.getFullName(".").plus("()")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
