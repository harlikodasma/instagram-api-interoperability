import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatError, MatInput } from '@angular/material/input';
import { NgIf } from '@angular/common';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatButton } from '@angular/material/button';
import { XmlRpcService } from '../../services/xml-rpc.service';
import { XML_RPC_METHOD_CALL } from '../../app.constants';
import { XmlParserService } from '../../services/xml-parser.service';
import { MatRadioButton, MatRadioGroup } from '@angular/material/radio';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from '@angular/material/table';
import { ViewMode } from '../../enum/view-mode.enum';

@Component({
  selector: 'app-xml-rpc',
  imports: [
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardTitle,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    NgIf,
    MatRadioButton,
    MatRadioGroup,
    FormsModule,
    MatError,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCellDef,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatRowDef,
    MatHeaderRowDef
  ],
  templateUrl: './xml-rpc.component.html',
  styleUrl: './xml-rpc.component.scss'
})
export class XmlRpcComponent implements OnInit {

  xmlRpcForm!: FormGroup;
  requestViewMode: ViewMode = ViewMode.SIMPLIFIED;
  responseRaw: string | null = null;
  responseSimplified: { city: string; temperature: string }[] = [];
  responseViewMode: ViewMode = ViewMode.SIMPLIFIED;
  displayedColumns: string[] = ['city', 'temperature'];
  readonly ViewMode: typeof ViewMode = ViewMode;

  constructor(
    private formBuilder: FormBuilder,
    private xmlRpcService: XmlRpcService,
    private xmlParserService: XmlParserService
  ) {}

  ngOnInit(): void {
    this.xmlRpcForm = this.formBuilder.group({
      cityName: ['', Validators.required],
      xml: [{ value: this.generateMethodCall(''), disabled: true }, Validators.required]
    });

    this.xmlRpcForm.get('cityName')?.valueChanges.subscribe((cityName: string): void => {
      this.xmlRpcForm.get('xml')?.setValue(this.generateMethodCall(cityName));
    });
  }

  onRequestViewChange(mode: ViewMode): void {
    this.requestViewMode = mode;
  }

  onResponseViewChange(mode: ViewMode): void {
    this.responseViewMode = mode;
  }

  sendXmlRpc(): void {
    if (this.xmlRpcForm.valid) {
      this.xmlRpcService.send(this.xmlRpcForm.get('xml')?.value).subscribe((result: string): void => {
        this.processResponse(result);
      });
    }
  }

  private generateMethodCall(cityName: string): string {
    return XML_RPC_METHOD_CALL.replace('{CITY_NAME}', cityName);
  }

  private processResponse(result: string): void {
    this.responseRaw = this.xmlParserService.beautifyXml(result);

    const parser = new DOMParser();
    const xmlDoc: Document = parser.parseFromString(result, 'text/xml');
    const values: HTMLCollectionOf<Element> = xmlDoc.getElementsByTagName('struct');

    this.responseSimplified = [];
    if (values.length > 0) {
      for (const value of values) {
        const city: string = value.getElementsByTagName('string')[0]?.textContent!;
        const temp: string = value.getElementsByTagName('double')[0]?.textContent!;
        this.responseSimplified.push({ city, temperature: `${temp}°C` });
      }
    }
  }
}
