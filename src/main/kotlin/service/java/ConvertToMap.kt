package main.kotlin.service.java
// ˅
import com.change_vision.jude.api.inf.model.IAssociation
import com.change_vision.jude.api.inf.model.INamedElement
import java.util.*
import java.text.*
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AssociationCollector
import main.kotlin.util.converter.JavaAssociationConverter

// ˄

class ConvertToMap : IMyPluginActionDelegate {
    // ˅
    
    // ˄

    override fun collectSelectedElements(): List<INamedElement> {
        // ˅
        return AssociationCollector().collectFromSelectedElements()
        // ˄
    }

    override fun excludeSetElements(selectedElements: List<INamedElement>): List<INamedElement> {
        // ˅
        val targetAssociations = mutableListOf<INamedElement>()

        for (selectedElement in selectedElements) {
            if (selectedElement !is IAssociation
                    || JavaAssociationConverter(selectedElement).isJavaCollectionKind(listOf(JavaAssociationConverter.CollectionKind.MAP))) {
                continue
            }
            targetAssociations.add(selectedElement)
        }

        return targetAssociations
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            JavaAssociationConverter(it as IAssociation).convertJavaCollectionKind(listOf(JavaAssociationConverter.CollectionKind.MAP))
            if (it.memberEnds[0].navigability == "Navigable") {
                println("Converted to Dictionary :".plus(it.memberEnds[0].getFullName(".")))
            } else {
                println("Converted to Dictionary :".plus(it.memberEnds[1].getFullName(".")))
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
