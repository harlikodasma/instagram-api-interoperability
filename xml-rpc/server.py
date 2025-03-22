from xmlrpc.server import SimpleXMLRPCServer
import requests
from xml.etree import ElementTree as ET

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

server = SimpleXMLRPCServer(("localhost", 8000))
server.register_function(get_temperatures, "getTemperatures")
print("Server started on port 8000")
server.serve_forever()
