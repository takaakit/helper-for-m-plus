package main.kotlin.service.cpp
// ˅
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.model.IAttribute
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.util.collector.AttributeCollector
import main.kotlin.service.IMyPluginActionDelegate
// ˄

class AddGetterSetter : IMyPluginActionDelegate {
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

        for (selectedElement in selectedElements) {
            var getterExists = false
            var setterExists = false
            for (operation in (selectedElement.owner as IClass).operations) {
                if (operation.name == "get".plus(selectedElement.name.replaceFirstChar(Char::titlecase))) { // Capitalize the first letter of the attribute name
                    getterExists = true
                } else if (operation.name == "set".plus(selectedElement.name.replaceFirstChar(Char::titlecase))) { // Capitalize the first letter of the attribute name
                    setterExists = true
                }
            }

            if (getterExists && setterExists) {
                continue
            }

            targetAttributes.add(selectedElement)
        }

        return targetAttributes
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            // Check the existence of getter and setter, because a getter or setter may exist
            var getterExists = false
            var setterExists = false
            for (operation in (it.owner as IClass).operations) {
                if (operation.name == "get".plus(it.name.replaceFirstChar(Char::titlecase))) { // Capitalize the first letter of the attribute name
                    getterExists = true
                } else if (operation.name == "set".plus(it.name.replaceFirstChar(Char::titlecase))) { // Capitalize the first letter of the attribute name
                    setterExists = true
                }
            }

            val basicModelEditor = ModelEditorFactory.getBasicModelEditor()

            // Add getter if getter does not exist
            if (!getterExists) {
                val getter = basicModelEditor.createOperation(
                        (it as IAttribute).owner as IClass,
                        "get".plus(it.name.replaceFirstChar(Char::titlecase)),    // Capitalize the first letter of the attribute name
                        it.type)
                println("Added getter :".plus(getter.getFullName(".").plus("()")))
            }

            // Add setter if setter does not exist
            if (!setterExists) {
                val setter = basicModelEditor.createOperation(
                        (it as IAttribute).owner as IClass,
                        "set".plus(it.name.replaceFirstChar(Char::titlecase)),    // Capitalize the first letter of the attribute name
                        "void")
                basicModelEditor.createParameter(setter, "value", it.type)
                println("Added setter :".plus(setter.getFullName(".").plus("()")))
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
