from xmlrpc.server import SimpleXMLRPCServer, SimpleXMLRPCRequestHandler
import requests
from xml.etree import ElementTree as ET

class CORSXMLRPCRequestHandler(SimpleXMLRPCRequestHandler):
    def end_headers(self):
        self.send_header('Access-Control-Allow-Origin', 'http://localhost:4200')
        self.send_header('Access-Control-Allow-Methods', 'POST, OPTIONS')
        self.send_header('Access-Control-Allow-Headers', 'Authorization, Content-Type')
        super().end_headers()

    def do_OPTIONS(self):
        self.send_response(200, "OK")
        self.end_headers()

def get_temperatures(city_name):
    response = requests.get("https://vrijeme.hr/hrvatska_n.xml")
    root = ET.fromstring(response.content)

    result = []
    for city in root.findall(".//Grad"):
        city_name_found = city.find("GradIme").text
        if city_name.lower() in city_name_found.lower():
            temperature = float(city.find("Podatci/Temp").text.strip())
            result.append({"city": city_name_found, "temperature": temperature})

    return result

server = SimpleXMLRPCServer(("localhost", 8000), requestHandler=CORSXMLRPCRequestHandler)
server.register_function(get_temperatures, "getTemperatures")
print("Server started on port 8000")
server.serve_forever()
