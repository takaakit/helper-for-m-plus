package main.kotlin.service.vdmpp
// ˅
import com.change_vision.jude.api.inf.model.INamedElement
import com.change_vision.jude.api.inf.model.IOperation
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.OperationCollector
import main.kotlin.util.converter.VdmppOperationConverter
// ˄

class ConvertToTotalFunction : IMyPluginActionDelegate {
    // ˅
    
    // ˄

    override fun collectSelectedElements(): List<INamedElement> {
        // ˅
        return OperationCollector().collectFromSelectedElements()
        // ˄
    }

    override fun excludeSetElements(selectedElements: List<INamedElement>): List<INamedElement> {
        // ˅
        val targetOperations = mutableListOf<INamedElement>()

        for (selectedElement in selectedElements) {
            if (selectedElement !is IOperation
                    || VdmppOperationConverter(selectedElement).isVdmppOperationKind(listOf(VdmppOperationConverter.OperationKind.TOTAL_FUNCTION))) {
                continue
            }
            targetOperations.add(selectedElement)
        }

        return targetOperations
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            VdmppOperationConverter(it as IOperation).convertVdmppOperationKind(listOf(VdmppOperationConverter.OperationKind.TOTAL_FUNCTION))
            println("Converted to total function :".plus(it.getFullName(".").plus("()")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
