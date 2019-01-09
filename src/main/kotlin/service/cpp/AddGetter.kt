package main.kotlin.service.cpp
// ˅
import main.kotlin.util.collector.AttributeCollector
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.model.IAttribute
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
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
                if (operation.name == "get".plus(selectedElement.name.capitalize())) {   // Capitalize the first letter of the attribute name
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
            val getter = basicModelEditor.createOperation(
                    (it as IAttribute).owner as IClass,
                    "get".plus(it.name.capitalize()),    // Capitalize the first letter of the attribute name
                    it.type)
            println("Added getter :".plus(getter.getFullName(".").plus("()")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
