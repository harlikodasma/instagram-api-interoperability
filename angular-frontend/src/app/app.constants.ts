export const API_BASE_URL: string = 'http://localhost:8080';
export const XML_RPC_BASE_URL: string = 'http://localhost:8000';

export const SAMPLE_XML_FOR_VALIDATION: string = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n' +
  '<userFollowers>\n' +
  '\t<data>\n' +
  '\t\t<edgeFollowedBy>\n' +
  '\t\t\t<count>650985951</count>\n' +
  '\t\t\t<pageInfo>\n' +
  '\t\t\t\t<hasNextPage>true</hasNextPage>\n' +
  '\t\t\t</pageInfo>\n' +
  '\t\t\t<edges>\n' +
  '\t\t\t\t<node>\n' +
  '\t\t\t\t\t<id>73492976468</id>\n' +
  '\t\t\t\t\t<username>matheuscardoso1.x</username>\n' +
  '\t\t\t\t\t<fullName>Matheus</fullName>\n' +
  '\t\t\t\t\t<profilePicUrl>https://scontent-qro1-2.cdninstagram.com/v/t51.2885-19/464760996_1254146839119862_3605321457742435801_n.png?stp=dst-jpg_e0_s150x150_tt6&amp;cb=8577c754-c2464923&amp;_nc_ht=scontent-qro1-2.cdninstagram.com&amp;_nc_cat=1&amp;_nc_oc=Q6cZ2QH8qowyCgQAo5_uw82fYpzD4VLrkLH91-Dq4FJ9LAXZXg-i7v06W_ogY2BpAsA6JOY&amp;_nc_ohc=JjupmNBvpWkQ7kNvgG-R9UM&amp;_nc_gid=Bddt6F5i1ckn8N6sQZ-OAg&amp;edm=AHBgTAQBAAAA&amp;ccb=7-5&amp;ig_cache_key=YW5vbnltb3VzX3Byb2ZpbGVfcGlj.3-ccb7-5-cb8577c754-c2464923&amp;oh=00_AYEraVa5vXxuM8Dd-76CZK6Ri2TX8i9i8Cu2-RZqaUAOzA&amp;oe=67E255A8&amp;_nc_sid=21e75c</profilePicUrl>\n' +
  '\t\t\t\t\t<isPrivate>false</isPrivate>\n' +
  '\t\t\t\t\t<isVerified>false</isVerified>\n' +
  '\t\t\t\t</node>\n' +
  '\t\t\t\t<node>\n' +
  '\t\t\t\t\t<id>69580007420</id>\n' +
  '\t\t\t\t\t<username>lilstadoesart</username>\n' +
  '\t\t\t\t\t<fullName>Lilsta</fullName>\n' +
  '\t\t\t\t\t<profilePicUrl>https://scontent-atl3-1.cdninstagram.com/v/t51.2885-19/464318946_1032920085243567_7463819551329738791_n.jpg?stp=dst-jpg_s150x150_tt6&amp;_nc_ht=scontent-atl3-1.cdninstagram.com&amp;_nc_cat=103&amp;_nc_oc=Q6cZ2QGC6hGgaQ1DMSdjvtiuzU1B-2BKvrDj5YdD2e1UCvBoC35XKXiQ38N2XGxxRFKsdn0&amp;_nc_ohc=VnqVbK1df5EQ7kNvgG3TpVu&amp;_nc_gid=C63AZiQlQy6oljlV9-aKOg&amp;edm=AOG-cTkBAAAA&amp;ccb=7-5&amp;oh=00_AYGLHXIpswlWqpa0qHIIXsQFYTWVuu1U--LglOG5iRmAGA&amp;oe=67E259B8&amp;_nc_sid=17ea04</profilePicUrl>\n' +
  '\t\t\t\t\t<isPrivate>false</isPrivate>\n' +
  '\t\t\t\t\t<isVerified>false</isVerified>\n' +
  '\t\t\t\t</node>\n' +
  '\t\t\t</edges>\n' +
  '\t\t</edgeFollowedBy>\n' +
  '\t</data>\n' +
  '\t<status>ok</status>\n' +
  '</userFollowers>';

export const SOAP_TEMPLATE = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://algebra.hr/springbackend/ws">
    <soapenv:Header/>
    <soapenv:Body>
        <ws:GetUserNodeByUsernameRequest>
            <ws:username>{USERNAME}</ws:username>
        </ws:GetUserNodeByUsernameRequest>
    </soapenv:Body>
</soapenv:Envelope>`;

export const XML_RPC_METHOD_CALL = `<?xml version="1.0"?>
<methodCall>
    <methodName>getTemperatures</methodName>
    <params>
        <param>
            <value><string>{CITY_NAME}</string></value>
        </param>
    </params>
</methodCall>`;

export const SAMPLE_JSON_FOR_REST = '{\n' +
  '  "data": {\n' +
  '    "edgeFollowedBy": {\n' +
  '      "count": 651076256,\n' +
  '      "edges": [\n' +
  '        {\n' +
  '          "node": {\n' +
  '            "id": 71143497082,\n' +
  '            "private": true,\n' +
  '            "fullName": "ディプ+ロハン🌷🌺....!",\n' +
  '            "username": "miss____dipannita",\n' +
  '            "verified": false,\n' +
  '            "profilePicUrl": "https://scontent-atl3-2.cdninstagram.com/v/t51.2885-19/483416236_1217350620016439_2120038212225645275_n.jpg?stp=dst-jpg_s150x150_tt6&_nc_ht=scontent-atl3-2.cdninstagram.com&_nc_cat=111&_nc_oc=Q6cZ2QFU9t_rXk0XO_O0wZkE17AHcgXLI3jpoChXhEieUEr5BOEahw78osCMoVsC3hLuzJo&_nc_ohc=qM9pX3ttZFgQ7kNvgEoB2Y6&_nc_gid=aR6Vqy5MgCmhxF8wUMPaag&edm=AOG-cTkBAAAA&ccb=7-5&oh=00_AYFxgLLN6F64Hmi5OKH4Q5rbv4h8SHUzD8GkXEXnA2IOLQ&oe=67E46821&_nc_sid=17ea04"\n' +
  '          }\n' +
  '        }\n' +
  '      ],\n' +
  '      "pageInfo": {\n' +
  '        "hasNextPage": true\n' +
  '      }\n' +
  '    }\n' +
  '  },\n' +
  '  "status": "ok"\n' +
  '}';
