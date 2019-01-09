package main.kotlin.service.python
// ˅
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.model.IAttribute
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AttributeCollector
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
                if (operation.name == selectedElement.name
                        && (operation.hasStereotype("decorator=property") || operation.getTaggedValue("decorator") == "property")) {
                    getterExists = true
                } else if (operation.name == selectedElement.name
                        && (operation.hasStereotype("decorator=".plus(selectedElement.name.plus(".setter"))) || operation.getTaggedValue("decorator") == selectedElement.name.plus(".setter"))) {
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
                if (operation.name == it.name
                        && (operation.hasStereotype("decorator=property") || operation.getTaggedValue("decorator") == "property")) {
                    getterExists = true
                } else if (operation.name == it.name
                        && (operation.hasStereotype("decorator=".plus(it.name.plus(".setter"))) || operation.getTaggedValue("decorator") == it.name.plus(".setter"))) {
                    setterExists = true
                }
            }

            val basicModelEditor = ModelEditorFactory.getBasicModelEditor()

            // Add getter if getter does not exist
            if (!getterExists) {
                val getter = basicModelEditor.createOperation((it as IAttribute).owner as IClass, it.name, "")
                getter.addStereotype("getter")
                basicModelEditor.createTaggedValue(getter, "decorator", "property")
                println("Added getter :".plus(getter.getFullName(".").plus("()")))
            }

            // Add setter if setter does not exist
            if (!setterExists) {
                val setter = basicModelEditor.createOperation((it as IAttribute).owner as IClass, it.name, "")
                setter.addStereotype("setter")
                basicModelEditor.createTaggedValue(setter, "decorator", it.getName() + ".setter")
                basicModelEditor.createParameter(setter, "value", "int")
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
