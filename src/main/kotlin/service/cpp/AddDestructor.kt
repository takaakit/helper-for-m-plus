package main.kotlin.service.cpp
// ˅
import main.kotlin.util.collector.ClassCollector
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
// ˄

class AddDestructor : IMyPluginActionDelegate {
    // ˅
    
    // ˄

    override fun collectSelectedElements(): List<INamedElement> {
        // ˅
        return ClassCollector().collectFromSelectedElements()
        // ˄
    }

    override fun excludeSetElements(selectedElements: List<INamedElement>): List<INamedElement> {
        // ˅
        val targetClasses = mutableListOf<INamedElement>()

        loop@ for (selectedElement in selectedElements) {
            if (selectedElement !is IClass
                    || selectedElement.owner is IClass) {
                continue@loop
            }

            for (operation in selectedElement.operations) {
                if (operation.name == "~".plus(selectedElement.name)) {
                    continue@loop
                }
            }

            targetClasses.add(selectedElement)
        }

        return targetClasses
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            val basicModelEditor = ModelEditorFactory.getBasicModelEditor()
            val destructor = basicModelEditor.createOperation(it as IClass, "~".plus(it.name), "")
            println("Added destructor :".plus(destructor.getFullName(".").plus("()")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
