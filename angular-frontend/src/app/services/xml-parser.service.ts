import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class XmlParserService {

  constructor() {}

  private tab: string = '    ';

  beautifyXml(xml: string): string {
    let indent: number = 0;
    const lines: string[] = xml
      .replace(/></g, '>\n<')
      .split('\n')
      .filter((line: string): string => line.trim());

    return lines
      .map((line: string): string => {
        if (line.startsWith('<?')) {
          return line;
        }

        if (line.startsWith('</')) {
          indent--;
        }
        const formattedLine: string = this.tab.repeat(Math.max(0, indent)) + line.trim();
        if (line.startsWith('<') && !line.endsWith('/>') && !line.includes('</')) {
          indent++;
        }

        return formattedLine;
      })
      .join('\n');
  }
}
