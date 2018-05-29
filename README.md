# GoBuilderGenerator

Plugin for generating Builder pattern code of Go struct from selected struct code.

## How to install

- in IntelliJ IDEA / GoLand : go to `Settings/Preferences → Plugins → Browse repositories` and search for `Go Builder Generator`

_or_

- download it and install via `Settings/Preferences → Plugins → Install plugin from disk`


## How to use it

1. Select the struct code

2. Shortcut: (Alt + B) **or** Menu: [Code] → [Generate Go Builder Pattern Code]

![demo_gif](pic/demo.gif)

## Demo

#### If we have a struct like this:

```
type CreateDisk struct {
	RegionID     string //required for creating CreateDisk struct
	ZoneID       string //required for creating CreateDisk struct
	DiskName     string
	Description  string
	Encrypted    bool
	DiskCategory string
	Size         int
	SnapshotID   string
	ClientToken  string
}
```

#### After generated, we got

- `type CreateDiskBuilder struct ` for storing *CreateDisk
- `func NewCreateDiskBuilder()` for creating a builder
- functions for receiving parameters
- `func Build()` for checking and building

```

// CreateDisk builder pattern code
type CreateDiskBuilder struct {
	createDisk *CreateDisk
}
```

```
func NewCreateDiskBuilder() *CreateDiskBuilder {
	createDisk := &CreateDisk{}
	b := &CreateDiskBuilder{createDisk: createDisk}
	return b
}
```

```
func (b *CreateDiskBuilder) RegionID(regionID string) *CreateDiskBuilder {
	b.createDisk.RegionID = regionID
	return b
}

func (b *CreateDiskBuilder) ZoneID(zoneID string) *CreateDiskBuilder {
	b.createDisk.ZoneID = zoneID
	return b
}

.
.
.

func (b *CreateDiskBuilder) ClientToken(clientToken string) *CreateDiskBuilder {
	b.createDisk.ClientToken = clientToken
	return b
}
```

```
func (b *CreateDiskBuilder) Build() (*CreateDisk, error) {
    // May miss required parameters
	return b.createDisk, nil
}
```

#### Use it

```
createDisk, err := NewCreateDiskBuilder().
	DiskName("test name").
	Description("desc").
	Size(20).
	SnapshotID("").
	Build()
if err != nil {
	// May miss required parameters
	return
}
// Do something with createDisk
```