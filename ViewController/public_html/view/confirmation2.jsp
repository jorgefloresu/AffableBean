
<div id="singleColumn">
    <p id="confirmationText">
        <strong>Your order has been successfully processed and will be delivered within 24 hours.</strong>
         
        <br/><br/>
         Please keep a note of your confirmation number: 
        <strong>
            ${orderRecord.confirmation_number}</strong>
         
        <br/>
         If you have a query concerning your order, feel free to 
        <a href="#">contact us</a>. 
        <br/><br/>
         Thank you for shopping at the Affable Bean Green Grocer!
    </p>
    <div class="summaryColumn">
        <table id="orderSummaryTable" class="detailsTable">
            <tr class="header">
                <th colspan="3">order summary</th>
            </tr>
             
            <tr class="tableHeading">
                <td>product</td>
                <td>quantity</td>
                <td>price</td>
            </tr>
             
             
            <tr class="lightBlue">
                <td colspan="3" style="padding: 0 20px">
                    <hr/>
                </td>
            </tr>
             
            <tr class="lightBlue">
                <td colspan="2" id="deliverySurchargeCellLeft">
                    <strong>delivery surcharge:</strong>
                </td>
                <td id="deliverySurchargeCellRight">
                    &euro; 
                    ${initParam.deliverySurcharge}
                </td>
            </tr>
             
            <tr class="lightBlue">
                <td colspan="2" id="totalCellLeft">
                    <strong>total:</strong>
                </td>
                <td id="totalCellRight">
                    &euro; 
                    ${orderRecord.amount}
                </td>
            </tr>
             
            <tr class="lightBlue">
                <td colspan="3" style="padding: 0 20px">
                    <hr/>
                </td>
            </tr>
             
            <tr class="lightBlue">
                <td colspan="3" id="dateProcessedRow">
                    <strong>date processed:</strong>
                    ${orderRecord.date_created}
                </td>
            </tr>
        </table>
    </div>
    
</div>