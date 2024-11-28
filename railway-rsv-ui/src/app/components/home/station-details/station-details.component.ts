import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {StationDetails} from "../../../models/station-details";

@Component({
    selector: 'app-station-details',
    imports: [
        FormsModule,
        NgForOf
    ],
    templateUrl: './station-details.component.html',
    styleUrl: './station-details.component.scss'
})
export class StationDetailsComponent {

    searchSaleText: string = '';
    sales?: StationDetails[];

    // Filter data based on searchText
    filteredData() {
        if (!this.searchSaleText) {
            return this.sales;
        }
        // @ts-ignore
        return this.sales.filter(sale => {
            const searchTerm = this.searchSaleText.toLowerCase();
            return (
                sale.medicineName.toLowerCase().includes(searchTerm) ||
                sale.saleDate.includes(searchTerm) ||
                sale.phoneNumber.toString().includes(searchTerm)
            );
        });
    }

}
