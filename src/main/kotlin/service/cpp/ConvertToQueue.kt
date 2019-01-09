package main.kotlin.service.cpp
// ˅
import com.change_vision.jude.api.inf.model.IAssociation
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AssociationCollector
import main.kotlin.util.converter.CppAssociationConverter
// ˄

class ConvertToQueue : IMyPluginActionDelegate {
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
                    || CppAssociationConverter(selectedElement).isCppCollectionKind(listOf(CppAssociationConverter.CollectionKind.QUEUE))) {
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
            CppAssociationConverter(it as IAssociation).convertCppCollectionKind(listOf(CppAssociationConverter.CollectionKind.QUEUE))
            if (it.memberEnds[0].navigability == "Navigable") {
                println("Converted to queue :".plus(it.memberEnds[0].getFullName(".")))
            } else {
                println("Converted to queue :".plus(it.memberEnds[1].getFullName(".")))
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
