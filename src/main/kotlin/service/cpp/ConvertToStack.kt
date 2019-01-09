package main.kotlin.service.cpp
// ˅
import com.change_vision.jude.api.inf.model.IAssociation
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AssociationCollector
import main.kotlin.util.converter.CppAssociationConverter
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
                    || CppAssociationConverter(selectedElement).isCppCollectionKind(listOf(CppAssociationConverter.CollectionKind.STACK))) {
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
            CppAssociationConverter(it as IAssociation).convertCppCollectionKind(listOf(CppAssociationConverter.CollectionKind.STACK))
            if (it.memberEnds[0].navigability == "Navigable") {
                println("Converted to stack :".plus(it.memberEnds[0].getFullName(".")))
            } else {
                println("Converted to stack :".plus(it.memberEnds[1].getFullName(".")))
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
