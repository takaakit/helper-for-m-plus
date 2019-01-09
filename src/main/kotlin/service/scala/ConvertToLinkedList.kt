package main.kotlin.service.scala
// ˅
import com.change_vision.jude.api.inf.model.IAssociation
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AssociationCollector
import main.kotlin.util.converter.ScalaAssociationConverter
// ˄

class ConvertToLinkedList : IMyPluginActionDelegate {
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
                    || ScalaAssociationConverter(selectedElement).isScalaCollectionKind(listOf(ScalaAssociationConverter.CollectionKind.LINKED_LIST))) {
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
            ScalaAssociationConverter(it as IAssociation).convertScalaCollectionKind(listOf(ScalaAssociationConverter.CollectionKind.LINKED_LIST))
            if (it.memberEnds[0].navigability == "Navigable") {
                println("Converted to LinkedList :".plus(it.memberEnds[0].getFullName(".")))
            } else {
                println("Converted to LinkedList :".plus(it.memberEnds[1].getFullName(".")))
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
