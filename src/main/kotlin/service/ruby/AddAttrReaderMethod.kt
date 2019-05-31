package main.kotlin.service.ruby
// ˅
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.model.IAttribute
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AttributeCollector

// ˄

class AddAttrReaderMethod : IMyPluginActionDelegate {
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
                if (operation.name == selectedElement.name && operation.hasStereotype("attr_reader")) {
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
            getter.addStereotype("attr_reader")
            println("Added attr_reader method :".plus(getter.getFullName(".")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
