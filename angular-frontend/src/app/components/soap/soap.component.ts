import { Component, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatError, MatInput } from '@angular/material/input';
import { NgIf } from '@angular/common';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatButton } from '@angular/material/button';
import { SoapService } from '../../services/soap.service';
import { SOAP_TEMPLATE } from '../../app.constants';
import { MatRadioButton, MatRadioGroup } from '@angular/material/radio';
import { XmlParserService } from '../../services/xml-parser.service';
import { ViewMode } from '../../enum/view-mode.enum';

@Component({
  selector: 'app-soap',
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
    MatError,
    MatFormField,
    NgIf,
    MatRadioGroup,
    FormsModule,
    MatRadioButton
  ],
  templateUrl: './soap.component.html',
  styleUrl: './soap.component.scss'
})
export class SoapComponent implements OnInit {

  soapForm!: FormGroup;
  requestViewMode: ViewMode = ViewMode.SIMPLIFIED;
  responseRaw: string | null = null;
  responseSimplified: string | null = null;
  responseViewMode: ViewMode = ViewMode.SIMPLIFIED;
  readonly ViewMode: typeof ViewMode = ViewMode;

  constructor(
    private formBuilder: FormBuilder,
    private soapService: SoapService,
    private xmlParserService: XmlParserService
  ) {}

  ngOnInit(): void {
    this.soapForm = this.formBuilder.group({
      username: ['', Validators.required],
      envelope: [{ value: this.generateSoapEnvelope(''), disabled: true }, Validators.required]
    });

    this.soapForm.get('username')?.valueChanges.subscribe((username: string): void => {
      this.soapForm.get('envelope')?.setValue(this.generateSoapEnvelope(username));
    });
  }

  onRequestViewChange(mode: ViewMode): void {
    this.requestViewMode = mode;
  }

  onResponseViewChange(mode: ViewMode): void {
    this.responseViewMode = mode;
  }

  sendSoap(): void {
    if (this.soapForm.valid) {
      this.soapService.send(this.soapForm.get('envelope')?.value).subscribe((result: string): void => {
        this.processResponse(result);
      });
    }
  }

  private generateSoapEnvelope(username: string): string {
    return SOAP_TEMPLATE.replace('{USERNAME}', username);
  }

  private processResponse(result: string): void {
    this.responseRaw = this.xmlParserService.beautifyXml(result);

    const parser = new DOMParser();
    const xmlDoc: Document = parser.parseFromString(result, 'text/xml');
    const responseNode: Element = xmlDoc.getElementsByTagName('user')[0] || xmlDoc.getElementsByTagName('error')[0];

    this.responseSimplified = this.xmlParserService.beautifyXml(new XMLSerializer().serializeToString(responseNode));
  }
}
