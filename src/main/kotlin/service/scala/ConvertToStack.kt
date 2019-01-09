package main.kotlin.service.scala
// ˅
import com.change_vision.jude.api.inf.model.IAssociation
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AssociationCollector
import main.kotlin.util.converter.ScalaAssociationConverter
// ˄

class ConvertToStack : IMyPluginActionDelegate {
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
                    || ScalaAssociationConverter(selectedElement).isScalaCollectionKind(listOf(ScalaAssociationConverter.CollectionKind.STACK))) {
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
            ScalaAssociationConverter(it as IAssociation).convertScalaCollectionKind(listOf(ScalaAssociationConverter.CollectionKind.STACK))
            if (it.memberEnds[0].navigability == "Navigable") {
                println("Converted to Stack :".plus(it.memberEnds[0].getFullName(".")))
            } else {
                println("Converted to Stack :".plus(it.memberEnds[1].getFullName(".")))
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
