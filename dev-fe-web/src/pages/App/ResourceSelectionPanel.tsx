import { useEffect, useRef, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  ChevronDown,
  ChevronUp,
  MonitorSmartphone,
  Package2,
  Trash2,
} from "lucide-react";
import { EquipmentService } from "@/services/admin/equipment.service";
import { ServiceService } from "@/services/admin/service.service";

interface Device {
  id: string;
  name: string;
  brand: string;
  imageUrls: string[];
}

interface Service {
  id: string;
  name: string;
  description: string;
  imageUrls: string[];
}

interface SelectedDevice extends Device {
  quantity: number;
}

interface SelectedService extends Service {
  quantity: number;
}

interface ResourceSelectionPanelProps {
  onSelectedDeviceIdsChange: (
    items: { equipmentId: string; quantity: number }[]
  ) => void;
  onSelectedServiceIdsChange: (
    items: { serviceId: string; quantity: number }[]
  ) => void;
}

export function ResourceSelectionPanel({
  onSelectedDeviceIdsChange,
  onSelectedServiceIdsChange,
}: ResourceSelectionPanelProps) {
  // Devices state
  const [devices, setDevices] = useState<Device[]>([]);
  const [deviceSearchKeyword, setDeviceSearchKeyword] = useState("");
  const devicePageRef = useRef(0);
  const deviceHasMoreRef = useRef(true);
  const deviceListRef = useRef<HTMLDivElement>(null);

  // Services state
  const [services, setServices] = useState<Service[]>([]);
  const [serviceSearchKeyword, setServiceSearchKeyword] = useState("");
  const servicePageRef = useRef(0);
  const serviceHasMoreRef = useRef(true);
  const serviceListRef = useRef<HTMLDivElement>(null);

  // Selected items (devices + services)
  const [selectedDevices, setSelectedDevices] = useState<SelectedDevice[]>([]);
  const [selectedServices, setSelectedServices] = useState<SelectedService[]>(
    []
  );

  const size = 10;
  const [collapsed, setCollapsed] = useState(true);

  const equipmentService = new EquipmentService();

  // Fetch devices
  const fetchDevices = async (
    keyword: string,
    page: number,
    size: number
  ): Promise<Device[]> => {
    const data = await equipmentService.getListEquipments(page, size, keyword);
    return data.content.map((device: any) => ({
      id: device.id,
      name: device.name,
      brand: device.brand,
      imageUrls: device.imageUrls || [],
    }));
  };

  // Fetch services
  const fetchServices = async (
    keyword: string,
    page: number,
    size: number
  ): Promise<Service[]> => {
    const serviceService = new ServiceService();
    const data = await serviceService.getListServices(page, size, keyword);
    return data.content.map((service: any) => ({
      id: service.id,
      name: service.name,
      description: service.description,
      imageUrls: service.imageUrls || [],
    }));
  };

  // Handle infinite scroll for devices
  const handleDeviceScroll = () => {
    const el = deviceListRef.current;
    if (!el || !deviceHasMoreRef.current) return;

    const scrollBottom = el.scrollHeight - el.scrollTop - el.clientHeight;
    if (scrollBottom < 5) {
      const nextPage = devicePageRef.current + 1;
      fetchDevices(deviceSearchKeyword.trim(), nextPage, size).then(
        (newData) => {
          if (newData.length === 0) {
            deviceHasMoreRef.current = false;
            return;
          }
          setDevices((prev) => [...prev, ...newData]);
          devicePageRef.current = nextPage;
        }
      );
    }
  };

  // Handle infinite scroll for services
  const handleServiceScroll = () => {
    const el = serviceListRef.current;
    if (!el || !serviceHasMoreRef.current) return;

    const scrollBottom = el.scrollHeight - el.scrollTop - el.clientHeight;
    if (scrollBottom < 5) {
      const nextPage = servicePageRef.current + 1;
      fetchServices(serviceSearchKeyword.trim(), nextPage, size).then(
        (newData) => {
          if (newData.length === 0) {
            serviceHasMoreRef.current = false;
            return;
          }
          setServices((prev) => [...prev, ...newData]);
          servicePageRef.current = nextPage;
        }
      );
    }
  };

  // Load devices & services when collapse mở hoặc keyword thay đổi
  useEffect(() => {
    if (!collapsed) {
      // reset pagination and hasMore
      devicePageRef.current = 0;
      deviceHasMoreRef.current = true;
      fetchDevices(deviceSearchKeyword.trim(), 0, size).then((data) => {
        setDevices(data);
        if (data.length < size) deviceHasMoreRef.current = false;
      });

      servicePageRef.current = 0;
      serviceHasMoreRef.current = true;
      fetchServices(serviceSearchKeyword.trim(), 0, size).then((data) => {
        setServices(data);
        if (data.length < size) serviceHasMoreRef.current = false;
      });
    } else {
      setDevices([]);
      setServices([]);
    }
  }, [collapsed, deviceSearchKeyword, serviceSearchKeyword]);

  // Attach scroll listeners
  useEffect(() => {
    const deviceEl = deviceListRef.current;
    const serviceEl = serviceListRef.current;

    if (!collapsed) {
      if (deviceEl) deviceEl.addEventListener("scroll", handleDeviceScroll);
      if (serviceEl) serviceEl.addEventListener("scroll", handleServiceScroll);
    }

    return () => {
      if (deviceEl) deviceEl.removeEventListener("scroll", handleDeviceScroll);
      if (serviceEl)
        serviceEl.removeEventListener("scroll", handleServiceScroll);
    };
  }, [collapsed]);

  // Handle checkbox change for device
  const handleDeviceCheckboxChange = (device: Device, checked: boolean) => {
    if (checked) {
      if (!selectedDevices.find((d) => d.id === device.id)) {
        const newSelected = [...selectedDevices, { ...device, quantity: 1 }];
        setSelectedDevices(newSelected);
        onSelectedDeviceIdsChange([
          ...newSelected.map((d) => ({
            equipmentId: d.id,
            quantity: d.quantity,
          }))
        ]);
      }
    } else {
      const newSelected = selectedDevices.filter((d) => d.id !== device.id);
      setSelectedDevices(newSelected);
      onSelectedDeviceIdsChange([
        ...newSelected.map((d) => ({
          equipmentId: d.id,
          quantity: d.quantity,
        }))
      ]);
    }
  };

  // Handle checkbox change for service
  const handleServiceCheckboxChange = (service: Service, checked: boolean) => {
    if (checked) {
      if (!selectedServices.find((s) => s.id === service.id)) {
        const newSelected = [...selectedServices, { ...service, quantity: 1 }];
        setSelectedServices(newSelected);
        onSelectedServiceIdsChange(
          newSelected.map((s) => ({
            serviceId: s.id,
            quantity: s.quantity,
          }))
        );
      }
    } else {
      const newSelected = selectedServices.filter((s) => s.id !== service.id);
      setSelectedServices(newSelected);
      onSelectedServiceIdsChange(
        newSelected.map((s) => ({
          serviceId: s.id,
          quantity: s.quantity,
        }))
      );
    }
  };

  // Remove device from selected
  const handleRemoveDevice = (id: string) => {
    const newSelected = selectedDevices.filter((d) => d.id !== id);
    setSelectedDevices(newSelected);
    onSelectedDeviceIdsChange([
      ...newSelected.map((d) => ({ equipmentId: d.id, quantity: d.quantity })),
      ...selectedDevices.map((s) => ({
        equipmentId: s.id,
        quantity: s.quantity,
      })),
    ]);
  };

  // Remove service from selected
  const handleRemoveService = (id: string) => {
    const newSelected = selectedServices.filter((s) => s.id !== id);
    setSelectedServices(newSelected);
    onSelectedServiceIdsChange(
      newSelected.map((s) => ({
        serviceId: s.id,
        quantity: s.quantity,
      }))
    );
  };

  // Update quantity for devices
  const updateDeviceQuantity = (id: string, delta: number) => {
    const updated = selectedDevices.map((d) =>
      d.id === id ? { ...d, quantity: Math.max(1, d.quantity + delta) } : d
    );
    setSelectedDevices(updated);
    onSelectedDeviceIdsChange([
      ...updated.map((d) => ({ equipmentId: d.id, quantity: d.quantity })),
      ...selectedDevices.map((s) => ({
        equipmentId: s.id,
        quantity: s.quantity,
      })),
    ]);
  };

  // Update quantity for services
  const updateServiceQuantity = (id: string, delta: number) => {
    const updated = selectedServices.map((s) =>
      s.id === id ? { ...s, quantity: Math.max(1, s.quantity + delta) } : s
    );
    setSelectedServices(updated);
    onSelectedServiceIdsChange([
      ...selectedServices.map((d) => ({
        serviceId: d.id,
        quantity: d.quantity,
      })),
      ...updated.map((s) => ({ serviceId: s.id, quantity: s.quantity })),
    ]);
  };

  return (
    <Card className="flex flex-col w-full border border-gray-400 dark:border-gray-600 rounded-lg shadow-sm">
      <CardHeader
        className="cursor-pointer px-6 py-4 border-b bg-muted/40 rounded-t-lg"
        onClick={() => setCollapsed(!collapsed)}
      >
        <div className="flex justify-between items-center w-full">
          {/* Bên trái: Icon + Tiêu đề */}
          <div className="flex items-center gap-2 text-xl font-semibold text-primary">
            <Package2 className="w-5 h-5 text-blue-600" />
            Tài nguyên khả dụng
          </div>

          {/* Bên phải: Nút toggle */}
          <button
            aria-label={collapsed ? "Mở rộng nội dung" : "Thu gọn nội dung"}
            className="bg-transparent border-none focus:outline-none p-1 rounded hover:bg-gray-200 dark:hover:bg-gray-700"
          >
            {collapsed ? (
              <ChevronDown className="w-5 h-5 text-gray-700 dark:text-gray-300" />
            ) : (
              <ChevronUp className="w-5 h-5 text-gray-700 dark:text-gray-300" />
            )}
          </button>
        </div>
      </CardHeader>

      {!collapsed && (
        <>
          <CardContent className="p-4 flex flex-col gap-4">
            <div className="flex gap-6 h-[360px]">
              {/* Devices List */}
              <div className="flex flex-col w-1/2 border border-gray-300 dark:border-gray-600 rounded-lg overflow-hidden">
                <input
                  type="text"
                  placeholder="Tìm thiết bị..."
                  className="bg-transparent p-2 border-b border-gray-300 dark:border-gray-600 outline-none"
                  value={deviceSearchKeyword}
                  onChange={(e) => {
                    deviceHasMoreRef.current = true;
                    setDeviceSearchKeyword(e.target.value);
                  }}
                />
                <div
                  ref={deviceListRef}
                  className="flex-1 overflow-y-auto bg-white dark:bg-gray-800"
                >
                  {devices.map((device) => {
                    const isChecked = selectedDevices.some(
                      (d) => d.id === device.id
                    );
                    return (
                      <label
                        key={device.id}
                        className="flex items-center gap-3 cursor-pointer border-b border-gray-200 dark:border-gray-700 p-2 hover:bg-gray-100 dark:hover:bg-gray-700"
                      >
                        <input
                          type="checkbox"
                          checked={isChecked}
                          onChange={(e) =>
                            handleDeviceCheckboxChange(device, e.target.checked)
                          }
                        />
                        <img
                          src={device.imageUrls?.[0] || "/placeholder.png"}
                          alt={device.name}
                          className="w-10 h-10 object-cover rounded"
                        />
                        <div className="flex flex-col">
                          <span className="font-semibold text-sm">
                            {device.name}
                          </span>
                          <span className="text-xs text-gray-500 dark:text-gray-400">
                            {device.brand}
                          </span>
                        </div>
                      </label>
                    );
                  })}
                  {devices.length === 0 && (
                    <p className="p-3 text-center text-gray-500 dark:text-gray-400">
                      Không có thiết bị phù hợp
                    </p>
                  )}
                </div>
              </div>

              {/* Services List */}
              <div className="flex flex-col w-1/2 border border-gray-300 dark:border-gray-600 rounded-lg overflow-hidden">
                <input
                  type="text"
                  placeholder="Tìm dịch vụ..."
                  className="bg-transparent p-2 border-b border-gray-300 dark:border-gray-600 outline-none"
                  value={serviceSearchKeyword}
                  onChange={(e) => {
                    serviceHasMoreRef.current = true;
                    setServiceSearchKeyword(e.target.value);
                  }}
                />
                <div
                  ref={serviceListRef}
                  className="flex-1 overflow-y-auto bg-white dark:bg-gray-800"
                >
                  {services.map((service) => {
                    const isChecked = selectedServices.some(
                      (s) => s.id === service.id
                    );
                    return (
                      <label
                        key={service.id}
                        className="flex items-center gap-3 cursor-pointer border-b border-gray-200 dark:border-gray-700 p-2 hover:bg-gray-100 dark:hover:bg-gray-700"
                      >
                        <input
                          type="checkbox"
                          checked={isChecked}
                          onChange={(e) =>
                            handleServiceCheckboxChange(
                              service,
                              e.target.checked
                            )
                          }
                        />
                        <img
                          src={service.imageUrls?.[0] || "/placeholder.png"}
                          alt={service.name}
                          className="w-10 h-10 object-cover rounded"
                        />
                        <div className="flex flex-col">
                          <span className="font-semibold text-sm">
                            {service.name}
                          </span>
                          <span className="text-xs text-gray-500 dark:text-gray-400">
                            {service.description || "Không có mô tả"}
                          </span>
                        </div>
                      </label>
                    );
                  })}
                  {services.length === 0 && (
                    <p className="p-3 text-center text-gray-500 dark:text-gray-400">
                      Không có dịch vụ phù hợp
                    </p>
                  )}
                </div>
              </div>
            </div>
          </CardContent>
          <CardContent className="p-2">
            <div
              className="grid grid-cols-2 gap-4 text-sm"
              style={{ height: "300px" }}
            >
              {selectedDevices.length === 0 &&
                selectedServices.length === 0 && (
                  <p className="text-gray-500 dark:text-gray-400 text-center italic col-span-2">
                    Chưa chọn thiết bị hoặc dịch vụ nào
                  </p>
                )}

              {(selectedDevices.length > 0 || selectedServices.length > 0) && (
                <>
                  {/* Selected Devices */}
                  <div className="flex flex-col">
                    <h3 className="font-semibold text-base mb-1">
                      Thiết bị đã chọn
                    </h3>
                    <div
                      className="flex flex-col gap-2 overflow-y-auto border-gray-300 dark:border-gray-600 rounded p-1"
                      style={{ maxHeight: "260px" }} // <= maxHeight cụ thể pixel
                    >
                      {selectedDevices.length === 0 && (
                        <p className="text-gray-500 dark:text-gray-400 italic text-center">
                          Chưa chọn thiết bị nào
                        </p>
                      )}
                      {selectedDevices.map((device) => (
                        <div
                          key={device.id}
                          className="flex justify-between items-center gap-2 border border-gray-300 dark:border-gray-600 rounded px-2 py-1"
                        >
                          {/* Nội dung bên trái */}
                          <div className="flex items-center gap-2 max-w-[180px] truncate">
                            <img
                              src={device.imageUrls?.[0] || "/placeholder.png"}
                              alt={device.name}
                              className="w-6 h-6 object-cover rounded"
                            />
                            <div className="flex flex-col truncate">
                              <span className="font-semibold truncate">
                                {device.name}
                              </span>
                              <span className="text-xs text-gray-500 dark:text-gray-400 truncate">
                                {device.brand}
                              </span>
                            </div>
                          </div>

                          {/* Nút tăng giảm và xóa bên phải */}
                          <div className="flex items-center gap-1">
                            <button
                              onClick={() =>
                                updateDeviceQuantity(device.id, -1)
                              }
                              className="bg-transparent btn-quantity p-0 w-5 h-5 flex items-center justify-center"
                              aria-label="Giảm số lượng"
                            >
                              –
                            </button>
                            <span className="w-4 text-center">
                              {device.quantity}
                            </span>
                            <button
                              onClick={() => updateDeviceQuantity(device.id, 1)}
                              className="bg-transparent btn-quantity p-0 w-5 h-5 flex items-center justify-center"
                              aria-label="Tăng số lượng"
                            >
                              +
                            </button>
                            <button
                              onClick={() => handleRemoveDevice(device.id)}
                              className="bg-transparent ml-1 text-red-600 hover:text-red-800 p-0"
                              aria-label="Xóa thiết bị"
                            >
                              <Trash2 size={14} />
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>
                  </div>

                  {/* Selected Services */}
                  <div className="flex flex-col">
                    <h3 className="font-semibold text-base mb-1">
                      Dịch vụ đã chọn
                    </h3>
                    <div
                      className="flex flex-col gap-2 overflow-y-auto border-gray-300 dark:border-gray-600 rounded p-1"
                      style={{ maxHeight: "260px" }} // <= maxHeight cụ thể pixel
                    >
                      {selectedServices.length === 0 && (
                        <p className="text-gray-500 dark:text-gray-400 italic text-center">
                          Chưa chọn dịch vụ nào
                        </p>
                      )}
                      {selectedServices.map((service) => (
                        <div
                          key={service.id}
                          className="flex justify-between items-center gap-2 border border-gray-300 dark:border-gray-600 rounded px-2 py-1"
                        >
                          {/* Nội dung bên trái */}
                          <div className="flex items-center gap-2 max-w-[430px] truncate">
                            <img
                              src={service.imageUrls?.[0] || "/placeholder.png"}
                              alt={service.name}
                              className="w-6 h-6 object-cover rounded"
                            />
                            <div className="flex flex-col truncate">
                              <span className="font-semibold truncate">
                                {service.name}
                              </span>
                              <span
                                className="text-xs text-gray-500 dark:text-gray-400 truncate"
                                title={service.description || "Không có mô tả"}
                              >
                                {service.description || "Không có mô tả"}
                              </span>
                            </div>
                          </div>

                          {/* Nút tăng giảm và xóa bên phải */}
                          <div className="flex items-center gap-1">
                            <button
                              onClick={() =>
                                updateServiceQuantity(service.id, -1)
                              }
                              className="bg-transparent btn-quantity p-0 w-5 h-5 flex items-center justify-center"
                              aria-label="Giảm số lượng dịch vụ"
                            >
                              –
                            </button>
                            <span className="w-4 text-center">
                              {service.quantity}
                            </span>
                            <button
                              onClick={() =>
                                updateServiceQuantity(service.id, 1)
                              }
                              className="bg-transparent btn-quantity p-0 w-5 h-5 flex items-center justify-center"
                              aria-label="Tăng số lượng dịch vụ"
                            >
                              +
                            </button>
                            <button
                              onClick={() => handleRemoveService(service.id)}
                              className="bg-transparent ml-1 text-red-600 hover:text-red-800 p-0"
                              aria-label="Xóa dịch vụ"
                            >
                              <Trash2 size={14} />
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>
                  </div>
                </>
              )}
            </div>
          </CardContent>
        </>
      )}
    </Card>
  );
}
